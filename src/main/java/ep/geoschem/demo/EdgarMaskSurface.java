package ep.geoschem.demo;

import java.io.IOException;

import ep.common.Grid;
import ep.common.GridSet;
import ep.common.Grids;
import ucar.ma2.InvalidRangeException;

/**
 * Create mask face for EDGAR
 */
public class EdgarMaskSurface {
  public static void main(String argv[]) throws IOException, InvalidRangeException {
    int shape[] = new int[] {90, 180};
    GridSet gs = new GridSet("data/EDGAR_Map.nc", shape);
    gs.open();

    Grid g = Grids.empty(shape);
    float arr[] = (float[])g.getSurface().getStorage();
    for (int i = 0; i < arr.length; ++i)
      arr[i] = 1;

    gs.addGridding("EDGAR_Map", null, g);
    gs.flushAndClose();
  }
}
