package ep.geoschem;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Configurations {
  public static GCConfiguration load(File filePath) throws IOException {
    ObjectMapper mapper = new ObjectMapper();
    GCConfiguration cf = mapper.readValue(filePath, GCConfiguration.class);
    return cf;
  }

  public static GCConfiguration load(String json) throws IOException {
    ObjectMapper mapper = new ObjectMapper();
    GCConfiguration cf = mapper.readValue(json, GCConfiguration.class);
    return cf;
  }
}
