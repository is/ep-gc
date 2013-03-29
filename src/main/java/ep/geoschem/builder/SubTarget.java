package ep.geoschem.builder;

import ep.common.ESID;

public class SubTarget {
  TargetHelper ti;
  ESID esid;

  public SubTarget(TargetHelper ti, ESID esid) {
    this.ti = ti;
    this.esid = esid;
  }


  public TargetHelper getTargetHelper() {
    return ti;
  }


  public ESID getEsid() {
    return esid;
  }
}
