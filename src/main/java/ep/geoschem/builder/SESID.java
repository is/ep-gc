package ep.geoschem.builder;

import ep.common.ESID;

public class SESID extends ESID {
  public String sourceSectors[];


  public SESID(ESID e) {
    super(e);
  }

  public SESID(ESID e, String[] sourceSectors) {
    super(e);
    this.sourceSectors = sourceSectors;
  }

  public SESID(String name, String date, String species, String sector, String[] sourceSectors) {
    super(name, date, species, sector);
    this.sourceSectors = sourceSectors;
  }
}
