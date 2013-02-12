package ep.geoschem;


import java.io.File;
import java.io.IOException;

public class Main {
  public static void main(String argv[]) throws IOException {
    GCConfiguration cf = GCConfiguration.load(new File("cfg.js"));

    System.out.println(cf);
    System.out.println(cf.root);
  }
}
