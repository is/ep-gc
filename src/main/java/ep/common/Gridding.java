package ep.common;

import ucar.ma2.Array;
import ucar.ma2.Index;

public class Gridding {
  public Array surface;

  public float baseLon;
  public float baseLat;

  public float resLon;
  public float resLat;

  public Array getSurface() {
    return surface;
  }

  public void setSurface(Array surface) {
    this.surface = surface;
  }

  public int[] getShape() {
    return surface.getShape();
  }

  public Index getIndex() {
    return surface.getIndex();
  }

  public void globalize() {
    baseLon = 0;
    baseLat = 0;

    int[] shape = getShape();
    resLat = 180.0f / shape[0];
    resLon = 360.0f / shape[1];
  }
}
