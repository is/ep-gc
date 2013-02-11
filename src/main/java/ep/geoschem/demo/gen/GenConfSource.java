package ep.geoschem.demo.gen;


import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;

@SuppressWarnings("unused")
public class GenConfSource {
  @JsonIgnore
  public GenConf up;

  public String name;
  public String timeScale;
  public int shape[];
  public String pathTemplate;

  public List<GenConfGrid> grids;

  public void init(Map<String, String> row) {
    name = row.get("SOURCE");
    timeScale = row.get("TIMESCALE");
    shape = new int[2];
    shape[0] = Integer.parseInt(row.get("LAT"));
    shape[1] = Integer.parseInt(row.get("LON"));
    pathTemplate = row.get("TEMPLATE");

    grids = new LinkedList<>();
  }

  public void addGrid(GenConfGrid grid) {
    grids.add(grid);
    grid.up = this;
  }


  public String getLat() {
    return Integer.toString(shape[0]);
  }

  public String getLon() {
    return Integer.toString(shape[1]);
  }

  public String getLatRes() {
    if (shape[0] == 90)
      return "2";
    if (shape[0] == 180)
      return "1";
    if (shape[0] == 360)
      return "0.5";
    if (shape[0] == 1800)
      return "0.1";

    return Float.toString(180.0f / shape[0]);
  }

  public String getLonRes() {
    if (shape[1] == 90)
      return "4";
    if (shape[1] == 180)
      return "2";
    if (shape[1] == 360)
      return "1";
    if (shape[1] == 720)
      return "0.5";
    if (shape[1] == 520)
      return "0.666";
    if (shape[1] == 1800)
      return "0.2";
    if (shape[1] == 3600)
      return "0.1";

    return Float.toString(180.0f / shape[0]);
  }
}
