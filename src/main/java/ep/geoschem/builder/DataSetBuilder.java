package ep.geoschem.builder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import ep.common.DateRange;
import ep.common.ESID;
import ep.geoschem.Configuration;
import ep.geoschem.Target;
import org.stringtemplate.v4.ST;

public class DataSetBuilder {
  Configuration conf;
  Target target;

  Map<String, List<ESID>> gridClusters;


  public Configuration getConf() {
    return conf;
  }

  public Target getTarget() {
    return target;
  }

  public List<ESID> getGridCluster(String fn) {
    return gridClusters.get(fn);
  }

  public DataSetBuilder(Configuration conf, Target target) {
    this.conf = conf;
    this.target = target;
  }


  public void initGridCluster() {
    gridClusters = new HashMap<>();
    DateRange range = new DateRange(target.beginDate, target.endDate);
    Splitter splitter = Splitter.on("|||");


    for (String date : range) {
    //  for (String es : conf.emissions) {
        for (String sector : conf.sectors) {
          for (String species : conf.species) {
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
      //}
    }

//    System.out.println(gridClusters.size() + " grid sets");
//    for (String s: gridClusters.keySet()) {
//      System.out.println(s);
//    }
  }


  public void build() throws Exception {
    initGridCluster();
    ArrayList<String> ncFiles = new ArrayList<>(gridClusters.keySet());

    GridSetBuilder gridSetBuilder = new GridSetBuilder(this);
    for (String ncFile: ncFiles) {
      gridSetBuilder.build(ncFile);
    }
  }
}
