package ep.geoschem.demo.gen;

import ep.common.ESID;
import ep.common.Grid;
import ep.common.Grids;

public class GenGridSimple implements GenGrid {
  @Override
  public Grid gen(GenConfGrid conf, ESID esid, String params) {
    Grid g = Grids.empty(conf.up.shape);
    float v = Float.parseFloat(params);
    float arr[] = (float[])g.getSurface().getStorage();
    int size = arr.length;
    for (int i = 0; i < size; ++i)
      arr[i] = v;
    return g;
  }
}
