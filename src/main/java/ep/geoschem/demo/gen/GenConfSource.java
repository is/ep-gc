package ep.geoschem.demo.gen;


import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
}
