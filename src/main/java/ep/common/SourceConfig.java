package ep.common;

import java.util.Map;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
  @JsonSubTypes.Type(value = SourceConfig.class, name = "base"),
  @JsonSubTypes.Type(value = FileSystemSourceConfig.class, name = "fs")
})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SourceConfig {
  public String name;
  public String timeScale;
  @JsonProperty("factor")
  public String factorArray;
  @JsonProperty("mask")
  public String maskArray;

  public Map<String, String> speciesAliases;
  public String timeFactorType;

  @JsonIgnore public Configuration up;
  @JsonIgnore public GridFactor timeFactor;
  @JsonIgnore public VocFactor vocFactor;
}
