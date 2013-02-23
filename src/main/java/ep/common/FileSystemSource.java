package ep.common;


import java.io.IOException;

import com.google.common.base.Splitter;
import org.stringtemplate.v4.ST;
import ucar.ma2.InvalidRangeException;

public class FileSystemSource implements Source {
  FileSystemSourceConfig cf;
  ST pathSTTemplate;
  Splitter pathSplitter;
  Grid factorArray;
  Grid maskArray;


  public FileSystemSource(FileSystemSourceConfig cf) throws IOException, InvalidRangeException {
    this.cf = cf;
    this.pathSplitter = Splitter.on("|||");

    this.pathSTTemplate = new ST(cf.pathTemplate);
    this.pathSTTemplate.add("cf", cf);
    this.pathSTTemplate.add("es", ST.EMPTY_ATTR);

    ESID esid = new ESID(cf.name, null, null, null);

    if (this.cf.factorArray != null) {
      factorArray = Grids.read(randerPath(this.cf.factorArray, esid));
    }

    if (this.cf.maskArray != null) {
      maskArray = Grids.read(randerPath(this.cf.maskArray, esid));
    }
  }


  public String randerPath(String template, ESID esid) {
    ST pathST = new ST(template);
    pathST.add("cf", cf);
    pathST.add("es", esid);
    return pathST.render();
  }


  public String randerPath(ESID esid) {
    ST pathST = new ST(pathSTTemplate);
    pathST.add("es", esid);
    return pathST.render();
  }


  @Override
  public Grid getGridding(ESID esid) throws Exception {
    if (null != cf.speciesAliases && cf.speciesAliases.get(esid.species) != null) {
      esid = new ESID(esid);
      esid.species = cf.speciesAliases.get(esid.species);
    }

    ESID oesid = esid;
    boolean isVoc = cf.vocFactor.isVoc(esid.species);

    if (isVoc) {
      esid = new ESID(esid);
      esid.species = cf.vocFactor.species;
    }

    String arrayPath = randerPath(esid);
    Grid g = Grids.read(arrayPath);
    if (factorArray != null) {
      g.floatScale(factorArray);
    }

    if (esid.date != null && esid.date.length() == 6 && cf.timeFactor != null) {
      cf.timeFactor.apply(esid, g);
    }

    if (isVoc) {
      cf.vocFactor.apply(oesid, g);
    }
    return g;
  }

  public Grid getMaskArray() {
    return maskArray;
  }
}
