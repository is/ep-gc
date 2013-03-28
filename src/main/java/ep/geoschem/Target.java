package ep.geoschem;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import ep.common.Rect;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({"name", "shape", "base", "beginDate", "endDate"})
public class Target {
  public String name;
  public String base;
  public String zorder[];
  public String species[];
  public String defaultEmission;
  public int[] shape;
  public String timeScale;
  public String pathTemplate;
  public String beginDate, endDate;
  public Rect clip;

  @JsonIgnore public Set<String> enabledSet;
  @JsonIgnore public GCConfiguration up;


  public void init() {

    if (beginDate.length() == 4) {
      timeScale = "year";
    } else {
      timeScale = "month";
    }

    if (zorder != null) {
      enabledSet = new HashSet<>(Arrays.asList(zorder));
    }
  }
}
