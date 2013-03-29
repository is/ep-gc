package ep.geoschem.demo;

import java.io.File;

import ep.geoschem.GCConfiguration;
import ep.geoschem.Main;
import ep.geoschem.Target;
import ep.geoschem.builder.DataSetBuilder;
import ep.geoschem.builder.TargetHelper;

public class T2Mask {
  public static void main(String argv[]) throws Exception {
    GCConfiguration cf = GCConfiguration.load(new File("conf/t2/cfg.js"));
    cf.init();

    if (cf.targets == null)
      cf.loadTargetConfig();

    for (Target target: cf.targets) {
      TargetHelper helper = new TargetHelper(cf, target);
      helper.initMaskArrays();
    }
  }
}
