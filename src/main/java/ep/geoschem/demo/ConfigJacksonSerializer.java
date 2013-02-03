package ep.geoschem.demo;

import java.util.HashMap;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import ep.common.Configuration;
import ep.common.EmissionSourceConfig;
import ep.common.FsEmissionSourceConfig;

public class ConfigJacksonSerializer {
  public static void main(String args[]) throws JsonProcessingException {
    Configuration cf = new Configuration();

    cf.root = "data";
    cf.emissions = new String[] {"EDGAR", "EMEP", "MEIC"};
    cf.sectors = new String[] {"power", "industry", "residential", "transporation", "agriculture"};

    EmissionSourceConfig esc0 = new EmissionSourceConfig();
    esc0.name = "MEIC";
    esc0.dateStep = "monthly";

    FsEmissionSourceConfig esc1 = new FsEmissionSourceConfig();
    esc1.name = "EDGAR";
    esc1.dateStep = "yearly";
    esc1.basePath = "data/in/edgar";

    cf.emissionConfig = new HashMap<String, EmissionSourceConfig>();
    cf.emissionConfig.put(esc0.name, esc0);
    cf.emissionConfig.put(esc1.name, esc1);

    ObjectMapper om = new ObjectMapper();
    om.configure(SerializationFeature.INDENT_OUTPUT, true);

    System.out.println(om.writeValueAsString(cf));
  }
}
