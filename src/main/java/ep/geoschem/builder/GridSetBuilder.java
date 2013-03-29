package ep.geoschem.builder;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import ep.common.ESID;
import ep.common.GridSet;
import ep.geoschem.GCConfiguration;
import ep.geoschem.Target;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.stringtemplate.v4.ST;

public class GridSetBuilder {
  static final Logger logger = LoggerFactory.getLogger(DataSetBuilder.class);
  DataSetBuilder parent;
  GCConfiguration conf;
  GridSet gs;

  public GridSetBuilder(DataSetBuilder parent) {
    this.parent = parent;
    this.conf = parent.getConf();
  }


  public DataSetBuilder getParent() {
    return parent;
  }


  public void build(String ncFilename) throws Exception {
    List<SubTarget> cluster = parent.getGridCluster(ncFilename);
    Splitter splitter = Splitter.on("|||");
    Target firstTarget = cluster.get(0).getTargetHelper().getTarget();


    logger.info("Generate:" + ncFilename);
    File file = new File(ncFilename);
    file.getParentFile().mkdirs();

    gs = new GridSet(ncFilename, firstTarget.shape, firstTarget.clip);
    gs.open();

    Map<String, SubTarget> subTasks = new HashMap<>();
    GridBuilder gridBuilder = new GridBuilder(this);

    // TODO check target shape
    for (SubTarget subTarget: cluster) {
      TargetHelper help = subTarget.getTargetHelper();

      Target target = help.getTarget();
      ESID esid = subTarget.getEsid();

      ST st = new ST(target.pathTemplate);
      st.add("cf", conf);
      st.add("ta", target);
      st.add("es", esid);

      String fullPath = st.render();
      List<String> tokens = Lists.newArrayList(splitter.split(fullPath));
      String varName = tokens.get(1);
      subTasks.put(varName, subTarget);
    }

    List<String> vars = new ArrayList<>(subTasks.keySet());
    Collections.sort(vars);

    for (String varName: vars) {
      gridBuilder.build(gs, varName, subTasks.get(varName));
    }
    gs.flushOut();
    gs.close();
  }
}
