package ep.geoschem.builder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import ep.common.DateRange;
import ep.common.ESID;
import ep.common.Grid;
import ep.common.Grids;
import ep.common.Source;
import ep.geoschem.GCConfiguration;
import ep.geoschem.Target;
import org.stringtemplate.v4.ST;

public class DataSetBuilder {
  GCConfiguration conf;
  Target target;

  Map<String, List<ESID>> gridClusters;
  Map<String, Grid> maskArrays;


  public GCConfiguration getConf() {
    return conf;
  }


  public Target getTarget() {
    return target;
  }


  public List<ESID> getGridCluster(String fn) {
    return gridClusters.get(fn);
  }


  public DataSetBuilder(GCConfiguration conf, Target target) {
    this.conf = conf;
    this.target = target;
  }


  public Grid getMaskArray(String sn) {
    return maskArrays.get(sn);
  }


  public void initMaskArrays() {
    maskArrays = new HashMap<>();
    Grid bake = Grids.empty(target.shape);

    if (conf.zorder == null)
      return;

    float bakeArr[] = (float[])bake.getSurface().getStorage();
    int size = bakeArr.length;

    for (String sname: conf.zorder) {
      Source s = conf.getEmissionSource(sname);
      Grid originMask = s.getMaskArray();
      if (originMask == null)
        continue;

      Grid realMask = Grids.empty(target.shape);
      Grids.maskRegrid(originMask, realMask);
      maskArrays.put(sname, realMask);

      int cClip = 0; // cliped
      int cSet = 0; // setup.

      float maskArr[] = (float[])realMask.getSurface().getStorage();
      for (int i = 0; i < size; ++i) {
        if (bakeArr[i] >= 1) {
          if (maskArr[i] != 0) {
            maskArr[i] = 0;
            ++cClip;
          }
        } else {
          if (maskArr[i] == 1) {
            bakeArr[i] = 1;
            ++cSet;
          }
        }
      }

    }
  }



  public void initGridCluster() {
    gridClusters = new HashMap<>();
    DateRange range = new DateRange(target.beginDate, target.endDate);
    Splitter splitter = Splitter.on("|||");

    List<String> speciesList = Lists.newArrayList(conf.species);
    if (conf.vocSpecies != null) {
      speciesList.addAll(Lists.newArrayList(conf.vocSpecies));
    }

    for (String date : range) {
      for (String sector : conf.sectors) {
        for (String species : speciesList) {
          ESID esid = new ESID(target.name, date, species, sector);

          ST st = new ST(target.pathTemplate);
          st.add("cf", conf);
          st.add("ta", target);
          st.add("es", esid);
          String oPath = st.render();

          List<String> oPathTokens = Lists.newArrayList(splitter.split(oPath));
          String ncPath = oPathTokens.get(0);
          // String varName = oPathTokens.get(1);
          List<ESID> cluster = gridClusters.get(ncPath);

          if (cluster == null) {
            cluster = new LinkedList<>();
            gridClusters.put(ncPath, cluster);
          }
          cluster.add(esid);
        }
      }
    }
  }


  public void build() throws Exception {
    initMaskArrays();
    initGridCluster();
    ArrayList<String> ncFiles = new ArrayList<>(gridClusters.keySet());
    Collections.sort(ncFiles);

    GridSetBuilder gridSetBuilder = new GridSetBuilder(this);
    for (String ncFile : ncFiles) {
      gridSetBuilder.build(ncFile);
    }
  }


}
