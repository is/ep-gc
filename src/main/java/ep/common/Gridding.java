package ep.common;

import ucar.ma2.Array;
import ucar.ma2.Index;

public class Gridding {
  public Array surface;

  public float baseLon;
  public float baseLat;

  public float resLon;
  public float resLat;

  public long getSize() {
    return surface.getSize();
  }

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


  private Gridding cloneWithProperties() {
    Gridding ng = new Gridding();

    ng.baseLat = baseLat;
    ng.baseLon = baseLon;

    ng.resLat = resLat;
    ng.resLon = resLon;

    return ng;
  }


  public Gridding empty() {
    Gridding ng = cloneWithProperties();
    ng.surface = Array.factory(surface.getElementType(), surface.getShape());
    return ng;
  }

  public Gridding copy() {
    Gridding ng = cloneWithProperties();
    ng.surface = surface.copy();
    return ng;
  }


  public void floatPlus(Gridding g) {
    if (g.getSize() != getSize()) {
      throw new IllegalArgumentException("not equal gridding added: " + getSize() + " + " + g.getSize());
    }

    long size = getSize();

    float[] addend = (float[])g.getSurface().getStorage();
    float[] augend = (float[])surface.getStorage();

    for (int i = 0; i < size; ++i) {
      augend[i] += addend[i];
    }
  }

  public Gridding remap(int lat, int lon) {
    Gridding g = Griddings.empty(float.class, lat, lon);
    Griddings.remap(g, this);
    return g;
  }
}
