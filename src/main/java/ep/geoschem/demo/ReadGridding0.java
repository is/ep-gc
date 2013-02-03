package ep.geoschem.demo;

import ep.common.Grid;
import ep.common.GridSet;
import ep.common.Grids;
import ep.common.PolarCoordinatesRegrid;
import ucar.ma2.Array;
import ucar.ma2.Index;
import ucar.nc2.NetcdfFile;

public class ReadGridding0 {
  public static void main(String args[]) throws Exception {
    NetcdfFile ncf = NetcdfFile.open("data/edgar/PM10/v42_PM10_2008_IPCC_1B2a_c_1A1b_c.0.1x0.1.nc");
    Grid g = Grids.read(ncf, "emi_pm10");
    ncf.close();

    Array arr = g.getSurface();

    int[] shape = arr.getShape();
    for (int i = 0; i < shape.length; ++i) {
      System.out.println(i + ":" + shape[i]);
    }

    System.out.println(Float.class.getName());
    System.out.println(arr.getElementType().getName());
    System.out.println(arr.getElementType());
    System.out.println(float.class.getName());
    System.out.println(arr.getFloat(1800 * 3600 - 1));
    System.out.println(arr.getFloat(1));

    Index ima = arr.getIndex();
    ima.set(1799, 3599);
    arr.getFloat(ima);

    g.globalize();
    System.out.println(g.resLat);

    Grid ng;
    ng = Grids.empty(float.class, 360, 540);
    // Grids.remap(g, ng);

    PolarCoordinatesRegrid regrid = new PolarCoordinatesRegrid(g.getShape(), ng.getShape());
    regrid.setup();
    regrid.extensityRegrid(g, ng);

    GridSet gs = new GridSet("test2.nc", ng.getShape());
    gs.open();
    gs.addGridding("s0", null, ng);
    gs.flushAndClose();
  }
}
