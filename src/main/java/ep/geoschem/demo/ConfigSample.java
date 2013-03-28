package ep.geoschem.demo;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.common.base.Joiner;
import ep.common.FileSystemSourceConfig;
import ep.geoschem.GCConfiguration;
import ep.geoschem.Target;
import ucar.ma2.InvalidRangeException;

public class ConfigSample {
  public static void main(String args[]) throws IOException, InvalidRangeException {
    GCConfiguration cf = new GCConfiguration();

    cf.root = "data";
    cf.conf = "conf";
    cf.defaultEmission = "EDGAR";
    cf.emissions = new String[] {"EDGAR", "EMEP", "MEIC"};
    cf.sectors = new String[] {"power", "industry", "residential", "transporation", "agriculture"};

    FileSystemSourceConfig esc0 = new FileSystemSourceConfig();
    esc0.name = "EMEP";
    esc0.timeScale = "year";
    esc0.basePath = "in";
    esc0.pathTemplate = "<cf.up.root>/<cf.basePath>/<es.name>/EMEP_CO_SOx_NH3_NOx_NMVOC_<es.date>_0.5x0.5.nc|||<es.species>_<es.sector>";
    esc0.speciesAliases = new HashMap<>();
    esc0.speciesAliases.put("SO2", "SOx");

    FileSystemSourceConfig esc1 = new FileSystemSourceConfig();
    esc1.name = "EDGAR";
    esc1.timeScale = "year";
    esc1.basePath = "in";
    esc1.pathTemplate = "<cf.up.root>/<cf.basePath>/<es.name>/<es.species>/" +
      "v42_<es.species>_<es.date>_IPCC_<es.sector>.0.1x0.1.nc|||" +
      "emi_<es.speciesLower>";

    cf.emissionConfigs = new HashMap<>();
    cf.emissionConfigs.put(esc0.name, esc0);
    cf.emissionConfigs.put(esc1.name, esc1);

    Target target = new Target();
    target.name = "o0";
    target.beginDate = "200301";
    target.endDate = "200412";
    target.base = "o0";
    target.timeScale = "month";
    target.shape = new int[] {900, 1800};
    target.zorder = new String[] {"EDGAR"};
    target.pathTemplate = "<ta.up.root>/<ta.base>/<es.name>/<es.date>.nc|||<es.species>_<es.sector>";
    target.init();

    cf.targets = new LinkedList<>();
    cf.targets.add(target);

    ObjectMapper om = new ObjectMapper();
    om.configure(SerializationFeature.INDENT_OUTPUT, true);
    om.configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, true);
    om.configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, true);

    String cfStr = om.writeValueAsString(cf);
    System.out.println(cfStr);
    GCConfiguration c2 = om.readValue(cfStr,GCConfiguration.class);

    System.out.println(c2.emissionConfigs.get("EDGAR").name);
    c2.init();

    System.out.println(c2);
    // String ss[] = c2.getSourceSectors("NH3", "Power", "EDGAR");
    System.out.println(Joiner.on(',').join(c2.getSourceSectors("NH3", "Power", "EDGAR")));
    System.out.println(c2.getYearIndex("EMEP", "1974"));
    System.out.println(c2.getYearIndex("EMEP", "2008"));

    c2.loadTargetConfig();
    System.out.println(om.writeValueAsString(c2));
  }
}
