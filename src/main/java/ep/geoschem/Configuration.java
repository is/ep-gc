package ep.geoschem;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.common.collect.Iterables;
import ep.common.EmissionSource;
import ep.common.EmissionSourceConfig;
import ep.common.FsEmissionSource;
import ep.common.FsEmissionSourceConfig;
import ucar.ma2.InvalidRangeException;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class Configuration {
  public String root;
  public String conf;
  public String defaultEmission;

  @JsonIgnore public String species[];
  @JsonIgnore public String sectors[];
  @JsonIgnore public String emissions[];
  @JsonIgnore public Map<String, EmissionSource> emissionSources;
  @JsonIgnore public Map<String, SectorTable> sectorMapper;

  @JsonProperty("esconf")
  public Map<String, EmissionSourceConfig> emissionConfigs;

  public static class SectorTable {
    public Map<String, String[]> sectors;
  }


  public void setup() throws IOException, InvalidRangeException {
    emissionSources = new HashMap<String, EmissionSource>();
    emissions = Iterables.toArray(emissionConfigs.keySet(), String.class);

    Arrays.sort(emissions);

    for (Map.Entry<String, EmissionSourceConfig> e : emissionConfigs.entrySet()) {
      EmissionSource es = null;
      EmissionSourceConfig esc = e.getValue();

      if (esc instanceof FsEmissionSourceConfig) {
        es = new FsEmissionSource((FsEmissionSourceConfig) esc);
      }

      if (es != null) {
        emissionSources.put(e.getKey(), es);
      }
    }


    HashSet species = new HashSet();
    HashSet sectors = new HashSet();

    sectorMapper = new HashMap<String, SectorTable>();
    CsvSchema schema = CsvSchema.emptySchema().withHeader();
    ObjectMapper mapper = new CsvMapper();

    MappingIterator<Map> it = mapper.reader(Map.class).
      with(schema).readValues(new File(conf, "sector_map.csv"));

    Splitter splitter = Splitter.on(',').trimResults();

    while (it.hasNext()) {
      Map<String, String> row = (Map<String, String>) it.next();
      String sp = (String) row.get("SPECIES");
      String es = (String) row.get("SOURCE");
      String st = (String) row.get("SECTOR");
      String ss = (String) row.get("SOURCESECTORS");

      if (Strings.isNullOrEmpty(sp))
        continue;
      if (Strings.isNullOrEmpty(ss))
        continue;

      species.add(sp);
      sectors.add(st);

      String key = sp + "," + st;
      SectorTable sm = sectorMapper.get(key);
      if (sm == null) {
        sm = new SectorTable();
        sm.sectors = new HashMap<String, String[]>();
        sectorMapper.put(key, sm);
      }

      String[] s = Iterables.toArray(splitter.split(ss), String.class);
      sm.sectors.put(es, s);
    }

    this.sectors = Iterables.toArray(sectors, String.class);
    this.species = Iterables.toArray(species, String.class);
  }
}
