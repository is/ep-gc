package ep.common;


import ucar.ma2.Array;

public interface EmissionSource {
  Array getArray(ESID esid);
}
