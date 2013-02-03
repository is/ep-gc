package ep.common;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class Configuration {
  public String root;

  public String species[];
  public String sectors[];
  public String emissions[];

  public Map<String, EmissionSourceConfig> emissionConfig;

  @JsonIgnore
  public Map<String, EmissionSource> emissionSource;
}
