package ep.common;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.fasterxml.jackson.databind.MappingIterator;
import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VocFactor implements GridFactor {
  static final Logger logger = LoggerFactory.getLogger(VocFactor.class);

  public Map<String, Float> vocMap;
  public Set<String> vocs;
  public String species;

  public void init(File fin) throws IOException {
    vocMap = new HashMap<>();
    vocs = new HashSet<>();
    species = "NMVOC";

    MappingIterator<Map<String, String>> it = CsvUtil.read(fin);

    while(it.hasNext()) {
      Map<String, String> row = it.next();
      String es = row.get("SOURCE");
      String st = row.get("SECTOR");

      if (Strings.isNullOrEmpty(es) || Strings.isNullOrEmpty(st))
        continue;

      for (Map.Entry<String, String> e: row.entrySet()) {
        if (Strings.isNullOrEmpty(e.getKey()))
          continue;

        if (e.getKey().equals("SOURCE") || e.getKey().equals("SECTOR"))
          continue;
        if (e.getKey().startsWith("__"))
          continue;

        vocs.add(e.getKey());
        String key = es + "," + st + "," + e.getKey();
        vocMap.put(key, Float.parseFloat(e.getValue()));
      }
    }
  }


  public void apply(ESID esid, Grid g) {
    Float F = vocMap.get(esid.name + "," + esid.sector + "," + esid.species);
    if (F == null) {
      logger.warn("VOC factor is not found, " +
        "name:" + esid.name + ", sector:" + esid.sector +
        ", species:" + esid.species);
      return;
    }
    g.floatScale(F);
  }


  public boolean isVoc(String s) {
    return vocs.contains(s);
  }
}
