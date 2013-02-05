package ep.geoschem.builder;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import ep.common.DateRange;
import ep.common.ESID;
import ep.geoschem.Configuration;
import ep.geoschem.Target;
import org.stringtemplate.v4.ST;

import java.util.*;

public class DataSetBuilder {
  Configuration conf;
  Target target;

  Map<String, List<ESID>> gridCluster;


  public DataSetBuilder(Configuration conf, Target target) {
    this.conf = conf;
    this.target = target;
  }


  public void initGridCluster() {
    gridCluster = new HashMap<String, List<ESID>>();
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
            List<ESID> cluster = gridCluster.get(ncPath);

            if (cluster == null) {
              cluster = new LinkedList<ESID>();
              gridCluster.put(ncPath, cluster);
            }
            cluster.add(esid);
          }
        }
      //}
    }

    System.out.println(gridCluster.size() + " grid sets");
    for (String s: gridCluster.keySet()) {
      System.out.println(s);
    }
  }
}
