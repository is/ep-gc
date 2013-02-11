package ep.geoschem.demo.gen;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import ep.common.Grid;
import ep.common.GridSet;
import ucar.ma2.InvalidRangeException;

public class Generater {
  GenConf cf;

  public void init(File conf) throws IOException {
    ObjectMapper om = new ObjectMapper();
    cf = om.readValue(conf, GenConf.class);
    cf.init();
  }

  public void init(File conf, String name) throws IOException {
    ObjectMapper om = new ObjectMapper();
    cf = om.readValue(conf, GenConf.class);
    cf.name = name;
    cf.init();
  }


  public void gen() throws IOException, InvalidRangeException {
    Map<String, List<GridTask>> taskCluster = new HashMap<>();
    Splitter commaSplitter = Splitter.on(',').trimResults();

    List<String> bigTasks = new LinkedList<>();

    for (Map.Entry<String, String> yie: cf.yearIndex.entrySet()) {
      if (yie.getValue().equals("0"))
        continue;

      List<String> yiTokens = Lists.newArrayList(commaSplitter.split(yie.getKey()));

      String sn = yiTokens.get(0);

      GenConfSource gcs = cf.getSource(sn);
      if (gcs == null)
        continue;

      bigTasks.add(yie.getKey());
    }


    List<String> dates = new ArrayList<>(20);

    for (String snyear: bigTasks) {
      List<String> yiTokens = Lists.newArrayList(commaSplitter.split(snyear));

      String sn = yiTokens.get(0);
      String year = yiTokens.get(1);

      GenConfSource gcs = cf.getSource(sn);
      dates.clear();

      if (gcs.timeScale.equals("month")) {
        for (int i = 1; i <= 12; ++i) {
          dates.add(String.format("%s%02d", year, i));
        }
      } else {
        dates.add(year);
      }

      for (String date: dates) {
        for (GenConfGrid gcf: gcs.grids) {
          GridTask task = new GridTask(gcf, date);
          task.init();

          List<GridTask> tasks = taskCluster.get(task.path);
          if (tasks == null) {
            tasks = new LinkedList<>();
            taskCluster.put(task.path, tasks);
          }
          tasks.add(task);
        }
      }
    }

    List<String> fns = new ArrayList<>(taskCluster.keySet());
    Collections.sort(fns);

    Comparator<GridTask> gridTaskComparator = new Comparator<GridTask>() {
      @Override
      public int compare(GridTask o1, GridTask o2) {
        return o1.var.compareTo(o2.var);
      }
    };

    GenGridSimple genGridSimple = new GenGridSimple();

    for (String fn: fns) {
      File outFile = new File(fn);
      outFile.getParentFile().mkdirs();
      List<GridTask> tasks = taskCluster.get(fn);

      GridSet gs = new GridSet(fn, tasks.get(0).cf.up.shape);
      gs.open();
      Collections.sort(tasks, gridTaskComparator);
      for (GridTask task: tasks) {
        GenGrid gg = null;

        if (task.cf.grid.equals("SIMPLE")) {
          gg = genGridSimple;
        }


        Grid g = gg.gen(task.cf, task.esid, task.cf.params);
        gs.addGridding(task.var, null, g);
      }
      gs.flushAndClose();
    }
  }
}
