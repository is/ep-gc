package ep.common;


import java.io.IOException;

import ucar.ma2.Array;

public interface EmissionSource {
  Gridding getGridding(ESID esid) throws Exception;
}
