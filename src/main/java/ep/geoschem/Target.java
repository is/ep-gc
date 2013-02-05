package ep.geoschem;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Target {
  public String name;
  public String base;
  public String enabled[];
  public int[] shape;
  public String dateStep;
  public String pathTemplate;
  public String beginDate, endDate;

  @JsonIgnore
  public Set<String> enabledSet;

  public void init() {
    if (enabled != null) {
      enabledSet = new HashSet<String>(Arrays.asList(enabled));
    }
  }
}
