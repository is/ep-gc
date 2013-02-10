package ep.geoschem.demo;


import ep.common.ESID;
import ep.common.FileSystemSource;
import ep.common.FileSystemSourceConfig;
import ep.common.Grid;
import ep.common.Grids;

public class EmissionSourceSample {
  public static void main(String args[]) throws Exception {
    FileSystemSourceConfig esConf = new FileSystemSourceConfig();
    esConf.basePath = "data/in";
    esConf.pathTemplate =
      "<cf.basePath>/<es.name>/<es.species>/" +
        "v42_<es.species>_<es.date>_IPCC_<es.sector>.0.1x0.1.nc|||" +
        "emi_<es.speciesLower>";

    FileSystemSource es = new FileSystemSource(esConf);
    Grid g; //  = es.getGridding(new ESID("edgar", "2004", "SO2", "7A"));
    g = es.getGridding(new ESID("edgar", "2004", "SO2", "7A"));
    System.out.println(g.getShape());
    System.out.println(g.getShape()[0] + ", " + g.getShape()[1]);

    g = Grids.getCombinedGridding(
      new int[]{360, 480}, es, "edgar", "2004", "SO2",
      new String[]{"1A1a_6C", "1A2"});

    System.out.println(g.getShape());
    System.out.println(g.getShape()[0] + ", " + g.getShape()[1]);
  }
}

