package ep.geoschem;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({"name", "shape", "base", "beginDate", "endDate"})
public class Target {
  public String name;
  public String base;
  public String enabled[];
  public int[] shape;
  public String dateStep;
  public String pathTemplate;
  public String beginDate, endDate;

  @JsonIgnore public Set<String> enabledSet;
  @JsonIgnore public Configuration up;

  public void init() {

    if (beginDate.length() == 4) {
      dateStep = "yearly";
    } else {
      dateStep = "monthly";
    }

    if (enabled != null) {
      enabledSet = new HashSet<>(Arrays.asList(enabled));
    }
  }
}
