package ep.geoschem.demo;

import java.io.IOException;
import java.util.HashMap;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import ep.geoschem.Configuration;
import ep.common.EmissionSourceConfig;
import ep.common.FsEmissionSourceConfig;
import ucar.ma2.InvalidRangeException;

public class ConfigJacksonSerializer {
  public static void main(String args[]) throws IOException, InvalidRangeException {
    Configuration cf = new Configuration();

    cf.root = "data";
    cf.conf = "conf";
    cf.defaultEmission = "EDGAR";
    cf.emissions = new String[] {"EDGAR", "EMEP", "MEIC"};
    cf.sectors = new String[] {"power", "industry", "residential", "transporation", "agriculture"};

    EmissionSourceConfig esc0 = new EmissionSourceConfig();
    esc0.name = "MEIC";
    esc0.dateStep = "monthly";

    FsEmissionSourceConfig esc1 = new FsEmissionSourceConfig();
    esc1.name = "EDGAR";
    esc1.dateStep = "yearly";
    esc1.basePath = "data/in/edgar";
    esc1.pathTemplate = "<cf.basePath>/<es.name>/<es.species>/" +
      "v42_<es.species>_<es.date>_IPCC_<es.sector>.0.1x0.1.nc|||" +
      "emi_<es.speciesLower>";

    cf.emissionConfigs = new HashMap<String, EmissionSourceConfig>();
    cf.emissionConfigs.put(esc0.name, esc0);
    cf.emissionConfigs.put(esc1.name, esc1);

    ObjectMapper om = new ObjectMapper();
    om.configure(SerializationFeature.INDENT_OUTPUT, true);

    String cfStr = om.writeValueAsString(cf);
    System.out.println(cfStr);
    Configuration c2 = om.readValue(cfStr,Configuration.class);

    System.out.println(c2.emissionConfigs.get("EDGAR").name);
    c2.setup();

    System.out.println(c2);
  }
}
