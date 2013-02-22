package ep.common;


public interface Source {
  Grid getGridding(ESID esid) throws Exception;
  Grid getMaskArray();
}
