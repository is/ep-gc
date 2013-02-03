package ep.common;


public interface EmissionSource {
  Grid getGridding(ESID esid) throws Exception;
}
