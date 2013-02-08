package ep.geoschem.demo;

import java.util.HashMap;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import ep.common.FileSystemEmissionSourceConfig;
import ep.geoschem.Configuration;
import ep.geoschem.Target;
import ep.geoschem.builder.DataSetBuilder;

public class BuilderSample {
  public static void main(String args[]) throws Exception {
    Configuration cf = new Configuration();

    cf.root = "data";
    cf.conf = "conf";
    cf.defaultEmission = "EDGAR";
    cf.emissions = new String[] {"EDGAR", "EMEP", "MEIC"};
    cf.sectors = new String[] {"power", "industry", "residential", "transporation", "agriculture"};

    FileSystemEmissionSourceConfig esc0 = new FileSystemEmissionSourceConfig();
    esc0.name = "EMEP";
    esc0.dateStep = "yearly";
    esc0.basePath = "data/in";
    esc0.pathTemplate = "<cf.basePath>/<es.nameLower>/EMEP_CO_SOx_NH3_NOx_NMVOC_<es.year>_0.5x0.5.nc|||<es.species>_<es.sector>";
    esc0.speciesAliases = new HashMap<>();
    esc0.speciesAliases.put("SO2", "SOx");

    FileSystemEmissionSourceConfig esc1 = new FileSystemEmissionSourceConfig();
    esc1.name = "EDGAR";
    esc1.dateStep = "yearly";
    esc1.basePath = "data/in";
    esc1.pathTemplate = "<cf.basePath>/<es.nameLower>/<es.species>/" +
      "v42_<es.species>_<es.year>_IPCC_<es.sector>.0.1x0.1.nc|||" +
      "emi_<es.speciesLower>";

    cf.emissionConfigs = new HashMap<>();
    cf.emissionConfigs.put(esc0.name, esc0);
    cf.emissionConfigs.put(esc1.name, esc1);

    ObjectMapper om = new ObjectMapper();
    om.configure(SerializationFeature.INDENT_OUTPUT, true);
    om.configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, true);
    om.configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, true);

    String cfStr = om.writeValueAsString(cf);
    System.out.println(cfStr);
    Configuration c2 = om.readValue(cfStr,Configuration.class);

    System.out.println(c2.emissionConfigs.get("EDGAR").name);
    c2.init();

    System.out.println(c2);
    System.out.println(c2.getSourceSectors("NH3", "Power", "EDGAR"));
    System.out.println(c2.getYearIndex("EMEP", "1974"));
    System.out.println(c2.getYearIndex("EMEP", "2008"));

    System.out.println(om.writeValueAsString(c2));

    Target target = new Target();
    target.name = "middle";
    target.beginDate = "2003";
    target.endDate = "2004";
    target.base = "data/o0";
    target.dateStep = "monthly";
    target.shape = new int[] {180, 360};
    target.enabled = new String[] {"EDGAR", };
    target.pathTemplate = "<ta.base>/<es.name>/<es.date>.nc|||<es.species>_<es.sector>";
    target.init();

    DataSetBuilder builder = new DataSetBuilder(c2, target);
    builder.initGridCluster();
    builder.build();
  }
}
