package ep.geoschem.demo.gen;

import java.util.List;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import ep.common.ESID;
import org.stringtemplate.v4.ST;

public class GridTask {
  public GenConfGrid cf;
  public String date;
  public String path;
  public String var;
  public ESID esid;

  public GridTask(GenConfGrid cf, String date) {
    this.cf = cf;
    this.date = date;
  }

  public void init() {
    GenConfSource gcs = cf.up;
    GenConf gc = gcs.up;

    ST template = new ST(cf.up.pathTemplate);
    esid = new ESID(cf.name, date, cf.species, cf.sector);

    template.add("cf", gcs);
    template.add("es", esid);

    String fullPath = template.render();

    List<String> tokens = Lists.newArrayList(Splitter.on("|||").split(fullPath));

    path = tokens.get(0);
    var = tokens.get(1);
  }
};
