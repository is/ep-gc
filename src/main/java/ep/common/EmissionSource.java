package ep.common;


import ucar.ma2.Array;

public interface EmissionSource {
  Array getSurface(String dsname, String sector, String species);
}
