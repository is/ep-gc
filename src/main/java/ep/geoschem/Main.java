package ep.geoschem;


import java.io.File;
import java.io.IOException;

import ep.geoschem.builder.DataSetBuilder;
import ucar.ma2.InvalidRangeException;

public class Main {
  GCConfiguration cf;

  public static void main(String argv[]) throws Exception {
    File cfFile;
    if (argv.length == 1) {
      cfFile = new File("conf",  argv[0] + "-cfg.js");
    } else {
      cfFile = new File("conf", "cfg.js");
    }

    Main app = new Main();
    app.init(cfFile);
    app.run();
  }

  public void init(File cfFile) throws IOException, InvalidRangeException {
    cf = GCConfiguration.load(cfFile);
    cf.init();
    cf.check();

    if (cf.targets == null)
      cf.loadTargetConfig();
  }

  public void run() throws Exception {
    for (Target target: cf.targets) {
      DataSetBuilder builder = new DataSetBuilder(cf, target);
      builder.build();
    }
  }
}
