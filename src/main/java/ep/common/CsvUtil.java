package ep.common;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

public class CsvUtil {
  public static MappingIterator<Map<String, String>> read(File fi) throws IOException {
    CsvSchema schema = CsvSchema.emptySchema().withHeader();
    ObjectMapper om = new CsvMapper();
    return om.reader(Map.class).with(schema).readValues(fi);
  }

  public static<T> MappingIterator<T> read(File fi, Class<T> clazz) throws IOException {
    CsvSchema schema = CsvSchema.emptySchema().withHeader();
    ObjectMapper om = new CsvMapper();
    return om.reader(clazz).with(schema).readValues(fi);
  }
}
