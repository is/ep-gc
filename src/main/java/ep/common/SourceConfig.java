package ep.common;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import ep.geoschem.Configuration;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
  @JsonSubTypes.Type(value = SourceConfig.class, name = "base"),
  @JsonSubTypes.Type(value = FileSystemSourceConfig.class, name = "fs")
})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SourceConfig {
  public String name;
  public String timeScale;
  public String factorArray;

  public Map<String, String> speciesAliases;
  public String timeFactorType;

  @JsonIgnore public Configuration up;
  @JsonIgnore public GridFactor timeFactor;
}
