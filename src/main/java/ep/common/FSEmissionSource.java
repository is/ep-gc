package ep.common;


import java.util.List;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import org.stringtemplate.v4.ST;
import ucar.nc2.NetcdfFile;

public class FsEmissionSource implements EmissionSource {
  FsEmissionSourceConfig conf;
  ST pathSTFactory;
  Splitter pathSplitter;

  public FsEmissionSource(FsEmissionSourceConfig conf) {
    this.conf = conf;
    this.pathSTFactory = new ST(conf.pathTemplate);
    this.pathSplitter = Splitter.on("|||");
  }

  public String randerPath(ESID esid) {
    ST pathST = new ST(pathSTFactory);
    pathST.add("es", esid);
    pathST.add("cf", conf);
    return pathST.render();
  }


  @Override
  public Gridding getGridding(ESID esid) throws Exception {
    String arrayPath = randerPath(esid);
    List<String> tokens = Lists.newArrayList(pathSplitter.split(arrayPath));

    // TODO add error handle for missing tokens
    String ncPath = tokens.get(0);
    String varName = tokens.get(1);

    NetcdfFile ncFile = NetcdfFile.open(ncPath);
    Gridding g = Griddings.read(ncFile, varName);
    ncFile.close();
    return g;
  }
}
