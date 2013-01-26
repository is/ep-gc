package ep.geoschem.demo;

import ep.common.Gridding;
import ep.common.Griddings;
import ucar.ma2.Array;
import ucar.ma2.Index;
import ucar.nc2.NetcdfFile;

public class ReadGridding0 {
  public static void main(String args[]) throws Exception {
    NetcdfFile ncf = NetcdfFile.open("data/edgar/PM10/v42_PM10_2008_IPCC_1B2a_c_1A1b_c.0.1x0.1.nc");
    Gridding g = Griddings.read(ncf, "emi_pm10");
    ncf.close();

    Array arr = g.getSurface();

    int[] shape = arr.getShape();
    for (int i = 0; i < shape.length; ++i) {
      System.out.println(i + ":" + shape[i]);
    }

    System.out.println(arr.getElementType().getName());
    System.out.println(arr.getFloat(1800 * 3600 - 1));
    System.out.println(arr.getFloat(1));

    Index ima = arr.getIndex();
    ima.set(1799, 3599);
    arr.getFloat(ima);

    g.globalize();
    System.out.println(g.resLat);
  }
}
