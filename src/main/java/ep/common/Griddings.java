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


  public static void remap(Gridding source, Gridding dest) {
    // Very simple & ugly remap function.
    // Only work on Globe surface combination
    Array srcSur = source.getSurface();
    Array destSur = dest.getSurface();

    int srcShape[] = srcSur.getShape();
    int destShape[] = destSur.getShape();

    int srcResLat = srcShape[0];
    int srcResLon = srcShape[1];

    int destResLat = destShape[0];
    int destResLon = destShape[1];

    double srcDeltaLat = 180.0 / srcResLat;
    double srcDeltaLon = 360.0 / srcResLon;

    double destDeltaLat = 180.0 / destResLat;
    double destDeltaLon = 360.0 / destResLon;

    double srcBaseLat = - srcDeltaLat * 0.5;
    double srcBaseLon;

    double destLimitLat = destDeltaLat;
    double destLimitLon;

    float[] srcArr = (float[])srcSur.getStorage();
    float[] destArr = (float[])destSur.getStorage();

    int os = -1;
    int od = 0;

    for (int j = 0; j < srcResLat; ++j) {
      srcBaseLat += srcDeltaLat;
      if (srcBaseLat > destLimitLat) {
        od += destResLon;
        destLimitLat += destDeltaLat;
      }


      // reset scan line
      srcBaseLon = - srcDeltaLon * 0.5;
      destLimitLon = destDeltaLon;
      int od2 = od;

      for (int i = 0; i < srcResLon; ++i) {
        os += 1;
        srcBaseLon += srcDeltaLon;

        if (srcBaseLon > destLimitLon) {
          destLimitLon += destDeltaLon;
          od2 += 1;
        }
        destArr[od2] += srcArr[os];
      }
    }
  }
}
