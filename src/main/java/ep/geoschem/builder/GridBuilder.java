package ep.geoschem.builder;

import ep.common.ESID;
import ep.common.Grid;
import ep.common.GridSet;
import ep.common.Grids;
import ep.geoschem.Configuration;
import ep.geoschem.Target;

public class GridBuilder {
  Configuration conf;
  Target target;

  NCBuilder parent;
  DataSetBuilder root;

  public GridBuilder(NCBuilder parent) {
    this.parent = parent;
    this.root = parent.getParent();

    this.conf = root.getConf();
    this.target = root.getTarget();
  }

  public void build(GridSet gs, String varName, ESID esid) {
    Grid resG = Grids.empty(target.shape);
    for (String es: conf.emissions) {
      if (target.enabledSet != null && !target.enabledSet.contains(es)) {
        continue;
      }


    }
  }
}
