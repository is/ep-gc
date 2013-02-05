package ep.common;


import java.io.IOException;

import com.google.common.base.Splitter;
import org.stringtemplate.v4.ST;
import ucar.ma2.InvalidRangeException;

public class FileSystemEmissionSource implements EmissionSource {
  FileSystemEmissionSourceConfig conf;
  ST pathSTTemplate;
  Splitter pathSplitter;
  Grid factoryArray;


  public FileSystemEmissionSource(FileSystemEmissionSourceConfig conf) throws IOException, InvalidRangeException {
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
    return g;
  }
}