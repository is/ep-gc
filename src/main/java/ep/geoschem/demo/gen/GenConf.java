package ep.geoschem.demo.gen;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.fasterxml.jackson.databind.MappingIterator;
import com.google.common.base.Strings;
import ep.common.CsvUtil;

public class GenConf {
  public String root;
  public String conf;
  public String name;


  Map<String, GenConfSource> sources;
  Map<String, String> yearIndex;

  public MappingIterator<Map<String, String>> readCsv(String id) throws IOException {
    return CsvUtil.read(new File(new File(conf, "gen"), name + "." + id + ".csv"));
  }


  public <T> MappingIterator<T> readCsv(String id, Class<T> clazz) throws IOException {
    return CsvUtil.read(new File(new File(conf, "gen"), name + "." + id + ".csv"), clazz);
  }


  public void init() throws IOException {
    initSources();
    initGrids();
    initYears();
  }

  private void initYears() throws IOException {
    Set<String> sns = sources.keySet();
    yearIndex = new HashMap<>();

    MappingIterator<Map<String, String>> it = readCsv("year");


    while (it.hasNext()) {
      Map<String, String> row = it.next();
      String year = row.get("YEAR");
      if (Strings.isNullOrEmpty((year)))
        continue;

      for (String sn : sns) {
        yearIndex.put(sn + "," + year, row.get(sn));
      }

    }
  }

  private void initGrids() throws IOException {
    MappingIterator<GenConfGrid> it = readCsv("grid", GenConfGrid.class);
    while (it.hasNext()) {
      GenConfGrid gcg = it.next();

      if (Strings.isNullOrEmpty(gcg.name))
        continue;
      if (Strings.isNullOrEmpty(gcg.species))
        continue;

      GenConfSource gcs = sources.get(gcg.name);
      if (sources == null)
        continue;
      gcs.addGrid(gcg);
    }
  }

  private void initSources() throws IOException {
    MappingIterator<Map<String, String>> it = readCsv("source");

    while (it.hasNext()) {
      Map<String, String> row = it.next();
      GenConfSource gcs = new GenConfSource();
      gcs.init(row);
      gcs.up = this;
      sources.put(gcs.name, gcs);
    }
  }
}
