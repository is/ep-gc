package ep.geoschem.builder;

import com.google.common.base.Joiner;
import ep.common.ESID;
import ep.common.Grid;
import ep.common.GridSet;
import ep.common.Grids;
import ep.common.Source;
import ep.geoschem.GCConfiguration;
import ep.geoschem.Target;

public class GridBuilder {
  GCConfiguration cf;
  Target target;

  GridSetBuilder parent;
  DataSetBuilder root;

  public GridBuilder(GridSetBuilder parent) {
    this.parent = parent;
    this.root = parent.getParent();

    this.cf = root.getConf();
    this.target = root.getTarget();
  }

  public void build(GridSet gs, String varName, ESID esid) throws Exception {

    Grid resG = Grids.empty(target.shape);

    for (String sn: cf.emissions) {
      if (target.enabledSet != null && !target.enabledSet.contains(sn)) {
        continue;
      }

      String[] ss;
      if (cf.vocFactor.isVoc(esid.species)) {
        ss = cf.getSourceSectors(cf.vocFactor.species, esid.sector, sn);
      } else {
        ss = cf.getSourceSectors(esid.species, esid.sector, sn);
      }

      if (ss == null)
        continue;

      System.out.format("build %s: %s, %s, %s - %s {%s}\n", varName,
        esid.date, esid.species, esid.sector, sn, Joiner.on(", ").join(ss));

      String year = esid.getYear();

      if (cf.getYearIndex(sn, year).equals(year)) {
        Grid eg = Grids.getCombinedGridding(target.shape,
          cf.getEmissionSource(sn), sn, esid.date, esid.species, ss);
        resG.floatPlus(eg);
        continue;
      }

      String yearBase = cf.getYearIndex(sn, year);
      String des = cf.defaultEmission;
      String[] dss = cf.getSourceSectors(esid.species, esid.sector, des);

      Grid baseFactor = Grids.getCombinedGridding(target.shape,
        cf.getEmissionSource(des), des, yearBase, esid.species, dss);

      Grid targetFactor = Grids.getCombinedGridding(target.shape,
        cf.getEmissionSource(des), des, year, esid.species, dss);

      Grid eg;
      if (esid.isYearly()) {
        eg = Grids.getCombinedGridding(target.shape,
          cf.getEmissionSource(sn), sn, yearBase, esid.species, ss);
      } else {
        eg = Grids.getCombinedGridding(target.shape,
          cf.getEmissionSource(sn), sn, yearBase + esid.getMonth(), esid.species, ss);
      }

      eg.floatScale2(baseFactor, targetFactor);
      resG.floatPlus(eg);
    }
    // TODO more var's attribute.
    gs.addGridding(varName, null, resG);
  }
}
