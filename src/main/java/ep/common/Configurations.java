package ep.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Configurations {
  public static Configuration load(File filePath) throws IOException {
    FileInputStream fis = new FileInputStream(filePath);
    String json = null;

    try {
      FileChannel fc = fis.getChannel();
      MappedByteBuffer bb = fc.map(FileChannel.MapMode.READ_ONLY, 0, fc.size());
      json = Charset.forName("utf-8").decode(bb).toString();
    } finally {
      fis.close();
    }

    if (json == null)
      return null;

    return load(json);
  }


  public static Configuration load(String json) throws IOException {
    ObjectMapper mapper = new ObjectMapper();
    Configuration cf = mapper.readValue(json, Configuration.class);
    return cf;
  }
}
