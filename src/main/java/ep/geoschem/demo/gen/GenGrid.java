package ep.geoschem.demo.gen;

import ep.common.ESID;
import ep.common.Grid;

public interface GenGrid {
  public Grid gen(GenConfGrid conf, ESID esid, String params);
}
