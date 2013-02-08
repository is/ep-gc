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
import ep.geoschem.Configuration;
import ep.geoschem.Target;
import org.stringtemplate.v4.ST;

public class NCBuilder {
  DataSetBuilder parent;

  Configuration conf;
  Target target;
  GridSet gs;

  public NCBuilder(DataSetBuilder parent) {
    this.parent = parent;
    this.target = parent.getTarget();
    this.conf = parent.getConf();
  }


  public DataSetBuilder getParent() {
    return parent;
  }


  public void build(String ncFilename) throws Exception {
    List<ESID> cluster = parent.getGridCluster(ncFilename);
    Splitter splitter = Splitter.on("|||");

    File file = new File(ncFilename);
    file.getParentFile().mkdirs();

    gs = new GridSet(ncFilename, target.shape);
    gs.open();

    Map<String, ESID> subTasks = new HashMap<>();
    GridBuilder gridBuilder = new GridBuilder(this);

    for (ESID esid: cluster) {
      ST st = new ST(target.pathTemplate);
      st.add("cf", conf);
      st.add("ta", target);
      st.add("es", esid);

      String fullPath = st.render();
      List<String> tokens = Lists.newArrayList(splitter.split(fullPath));
      // String ncPath = tokens.get(0);
      String varName = tokens.get(1);
      subTasks.put(varName, esid);
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
