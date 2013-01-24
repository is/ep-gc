package ep.common;


import ucar.ma2.Array;

public interface EmissionDataSet {
  Array getSurface(String dsname, String sector, String species);
}
