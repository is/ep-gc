package ep.geoschem.builder;

import java.util.HashMap;
import java.util.Map;

import ep.common.ESID;
import ep.common.Grid;
import ep.common.GridSet;
import ep.common.Grids;
import ep.geoschem.GCConfiguration;
import ep.geoschem.Target;

public class GridBuilder {
  GCConfiguration cf;

  GridSetBuilder parent;
  DataSetBuilder root;

  public GridBuilder(GridSetBuilder parent) {
    this.parent = parent;
    this.root = parent.getParent();
    this.cf = root.getConf();
  }

  public void build(GridSet gs, String varName, SubTarget sTarget) throws Exception {
    TargetHelper helper = sTarget.getTargetHelper();
    Target target = helper.getTarget();
    Grid resG = Grids.empty(target.shape);
    ESID esid = sTarget.getEsid();

    String emissions[];

    if (target.zorder != null) {
      emissions = target.zorder;
    } else {
      emissions = cf.zorder;
    }

    for (String sn : emissions) {
      Grid maskArray = helper.getMaskArray(sn);
      String[] ss = cf.getSourceSectors(esid.species, esid.sector, sn);

      if (ss == null) {
        throw new IllegalArgumentException("null/empty source sector set, " +
          "name="+ sn + ",species=" + esid.species + ",sector=" + esid.sector);
      }

      String year = esid.getYear();

      Grid eg;

      if (cf.getYearIndex(sn, year).equals(year)) {
        eg = Grids.getCombinedGridding(target.shape,
          cf.getEmissionSource(sn), sn, esid.date, esid.species, ss);
      } else {
        String yearBase = cf.getYearIndex(sn, year);

        String des = target.defaultEmission;
        if (des == null)
          des = cf.defaultEmission;

        String[] dss = cf.getSourceSectors(esid.species, esid.sector, des);

        Grid baseFactor = Grids.getCombinedGridding(target.shape,
          cf.getEmissionSource(des), des, yearBase, esid.species, dss);

        Grid targetFactor = Grids.getCombinedGridding(target.shape,
          cf.getEmissionSource(des), des, year, esid.species, dss);


        if (esid.isYearly()) {
          eg = Grids.getCombinedGridding(target.shape,
            cf.getEmissionSource(sn), sn, yearBase, esid.species, ss);
        } else {
          eg = Grids.getCombinedGridding(target.shape,
            cf.getEmissionSource(sn), sn, yearBase + esid.getMonth(), esid.species, ss);
        }
        eg.floatScale2(baseFactor, targetFactor);
      }

      if (maskArray != null) {
        resG.floatPlusWithFactor(eg, maskArray);
      } else {
        resG.floatPlus(eg);
      }
    }

    Map<String, String> attrs = new HashMap<>();

    attrs.put("long_name", varName);
    attrs.put("short_name", esid.name + "__" + varName);
    attrs.put("unit", "mg yr-1");

    if (target.clip != null) {
      gs.addArray(varName, attrs,  resG.arrayClip(target.clip));
    } else {
      gs.addGridding(varName, attrs, resG);
    }
  }
}
