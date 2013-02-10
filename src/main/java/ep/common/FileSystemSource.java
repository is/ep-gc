package ep.common;


import java.io.IOException;

import com.google.common.base.Splitter;
import org.stringtemplate.v4.ST;
import ucar.ma2.InvalidRangeException;

public class FileSystemSource implements Source {
  FileSystemSourceConfig conf;
  ST pathSTTemplate;
  Splitter pathSplitter;
  Grid factoryArray;


  public FileSystemSource(FileSystemSourceConfig conf) throws IOException, InvalidRangeException {
    this.conf = conf;
    this.pathSplitter = Splitter.on("|||");

    this.pathSTTemplate = new ST(conf.pathTemplate);
    this.pathSTTemplate.add("cf", conf);
    this.pathSTTemplate.add("es", ST.EMPTY_ATTR);

    if (this.conf.factorArray != null) {
      factoryArray = Grids.read(this.conf.factorArray);
    }
  }

  public String randerPath(ESID esid) {
    ST pathST = new ST(pathSTTemplate);
    pathST.add("es", esid);
    return pathST.render();
  }


  @Override
  public Grid getGridding(ESID esid) throws Exception {
    if (null != conf.speciesAliases && conf.speciesAliases.get(esid.species) != null) {
      esid = new ESID(esid);
      esid.species = conf.speciesAliases.get(esid.species);
    }

    String arrayPath = randerPath(esid);
    Grid g = Grids.read(arrayPath);
    if (factoryArray != null) {
      g.floatScale(factoryArray);
    }

    if (esid.date != null && esid.date.length() == 6 && conf.timeFactor != null) {
      conf.timeFactor.apply(esid, g);
    }
    return g;
  }
}
