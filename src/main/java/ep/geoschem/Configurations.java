package ep.geoschem;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Configurations {
  public static Configuration load(File filePath) throws IOException {
    ObjectMapper mapper = new ObjectMapper();
    Configuration cf = mapper.readValue(filePath, Configuration.class);
    return cf;
  }

  public static Configuration load(String json) throws IOException {
    ObjectMapper mapper = new ObjectMapper();
    Configuration cf = mapper.readValue(json, Configuration.class);
    return cf;
  }
}
