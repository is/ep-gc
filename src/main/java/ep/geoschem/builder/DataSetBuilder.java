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
import ep.geoschem.GCConfiguration;
import ep.geoschem.Target;
import org.stringtemplate.v4.ST;

public class DataSetBuilder {
  GCConfiguration conf;
  Target target;

  Map<String, List<ESID>> gridClusters;


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
    initGridCluster();
    ArrayList<String> ncFiles = new ArrayList<>(gridClusters.keySet());
    Collections.sort(ncFiles);

    GridSetBuilder gridSetBuilder = new GridSetBuilder(this);
    for (String ncFile : ncFiles) {
      gridSetBuilder.build(ncFile);
    }
  }
}
