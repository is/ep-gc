package ep.common;


import java.io.IOException;

import ucar.ma2.Array;
import ucar.ma2.InvalidRangeException;
import ucar.nc2.NetcdfFile;

public class Griddings {
  public static Gridding read(NetcdfFile ncfile, String variable) throws IOException, InvalidRangeException {
    Array array = ncfile.readSection(variable);

    Gridding g = new Gridding();
    g.setSurface(array);
    return g;
  }
}
