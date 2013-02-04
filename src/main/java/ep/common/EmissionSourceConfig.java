package ep.common;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
  @JsonSubTypes.Type(value = EmissionSourceConfig.class, name = "base"),
  @JsonSubTypes.Type(value = FsEmissionSourceConfig.class, name = "fs")
})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EmissionSourceConfig {
  @JsonIgnore
  public boolean enable;

  public String name;
  public String dateStep;
  public String factorArray;

  public Map<String, String> speciesAliases;
}
