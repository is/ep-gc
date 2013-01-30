package ep.geoschem.demo;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

public class ReaderCSV {
  public static void main(String[] args) throws IOException {
    CsvSchema schema = CsvSchema.emptySchema().withHeader();
    ObjectMapper mapper = new CsvMapper();

    MappingIterator<Map> it =  mapper.reader(Map.class).
      with(schema).readValues(new File("conf/sector_map.csv"));

    while(it.hasNext()) {
      Map<String, Object> m = it.next();
      if (m.get("SPECIES").equals(""))
        continue;
      System.out.println(m.get("SPECIES") + ":" + m.get("SOURCE"));
    }
  }
}
