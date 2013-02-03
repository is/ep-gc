package ep.common;


import java.util.List;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import org.stringtemplate.v4.ST;
import ucar.nc2.NetcdfFile;

public class FsEmissionSource implements EmissionSource {
  FsEmissionSourceConfig conf;
  ST pathSTTemplate;
  Splitter pathSplitter;
  Gridding factoryArray;


  public FsEmissionSource(FsEmissionSourceConfig conf) {
    this.conf = conf;
    this.pathSplitter = Splitter.on("|||");

    this.pathSTTemplate = new ST(conf.pathTemplate);
    this.pathSTTemplate.add("cf", conf);
    this.pathSTTemplate.add("es", ST.EMPTY_ATTR);


  }

  public String randerPath(ESID esid) {
    ST pathST = new ST(pathSTTemplate);
    pathST.add("es", esid);
    return pathST.render();
  }


  @Override
  public Gridding getGridding(ESID esid) throws Exception {
    String arrayPath = randerPath(esid);
    return Griddings.read(arrayPath);
  }
}
