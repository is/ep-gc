package ep.geoschem.demo.gen;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class GenConfGrid {
  @JsonIgnore
  public GenConfSource up;

  @JsonProperty("SOURCE")
  public String name;
  @JsonProperty("SPECIES")
  public String species;
  @JsonProperty("SECTOR")
  public String sector;
  @JsonProperty("GRID")
  public String grid;
  @JsonProperty("PARAMS")
  public String params;
}
