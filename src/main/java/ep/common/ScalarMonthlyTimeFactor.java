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

public class ScalarMonthlyTimeFactor implements GridFactor {

  public Map<String, float[]> factors;

  public void loadFromCSV(File fin) throws IOException {
    CsvSchema schema = CsvSchema.emptySchema().withHeader();
    ObjectMapper mapper = new CsvMapper();
    factors = new HashMap<>();

    MappingIterator<Map> it = mapper.reader(Map.class).
      with(schema).readValues(fin);

    while(it.hasNext()) {
      Map<String, String> row = (Map<String, String>)it.next();
      String es = row.get("SOURCE");
      String sp = row.get("SPECIES");
      String st = row.get("SECTOR");

      if (Strings.isNullOrEmpty(es))
        continue;

      String key = es + "," + sp + "," + st;
      float farray[] = new float[12];

      for (int i = 0; i < 12; i++) {
        farray[i] = Float.parseFloat(row.get(Integer.toString(i + 1)));
      }
      factors.put(key, farray);
    }
  }


  @Override
  public void apply(ESID esid, Grid g) {
    String key = esid.name + "," + esid.species + "," + esid.sector;
    int month = Integer.parseInt(esid.date.substring(4, 6));

    float farray[] = factors.get(key);
    if (farray == null)
      return;

    float  factor = farray[month - 1];

    int size = (int)g.getSize();
    float[] arr = (float[])g.getSurface().getStorage();
    for (int i = 0; i < size; ++i) {
      arr[i] *= factor;
    }
  }
}
