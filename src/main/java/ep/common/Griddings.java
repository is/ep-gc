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
    g.globalize();
    return g;
  }


  public static Gridding empty(Class typeClass, int lat, int lon) {
    int shape[] = new int[] {lat, lon};
    Array surface = Array.factory(typeClass, shape);
    Gridding g = new Gridding();

    g.setSurface(surface);
    g.globalize();
    return g;
  }
}
