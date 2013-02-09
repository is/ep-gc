package ep.common;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.google.common.base.Strings;

public class VocFactor_ implements GridFactor {
  public Map<String, Float> vocMap;

  public void loadFromCSV(File fin) throws IOException {
    CsvSchema schema = CsvSchema.emptySchema().withHeader();
    ObjectMapper mapper = new CsvMapper();

    vocMap = new HashMap<>();
    MappingIterator<Map<String, String>> it = mapper.reader(Map.class).
      with(schema).readValues(fin);

    while(it.hasNext()) {
      Map<String, String> row = it.next();
      String es = row.get("SOURCE");
      String st = row.get("SECTOR");

      if (Strings.isNullOrEmpty(es) || Strings.isNullOrEmpty(st))
        continue;

      for (Map.Entry<String, String> e: row.entrySet()) {
        if (e.getKey().equals("SOURCE") || e.getKey().equals("SECTOR"))
          continue;
        if (e.getKey().startsWith("__"))
          continue;

        String key = es + "," + st + "," + e.getKey();
        vocMap.put(key, Float.parseFloat(e.getValue()));
      }
    }
  }

  public void apply(ESID esid, Grid g) {
    Float F = vocMap.get(esid.name + "," + esid.species);
    if (F == null)
      return;

    g.floatScale(F);
  }
}
