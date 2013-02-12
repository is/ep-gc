package ep.geoschem.demo;

import java.io.File;

import ep.geoschem.Main;

public class T2 {
  public static void main(String argv[]) throws Exception {
    Main app = new Main();
    app.init(new File("conf/t2/cfg.js"));
    app.run();
  }
}
