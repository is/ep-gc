package ep.geoschem.builder;

import java.util.HashMap;
import java.util.Map;

import ep.common.Grid;
import ep.common.Grids;
import ep.common.Source;
import ep.geoschem.GCConfiguration;
import ep.geoschem.Target;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TargetHelper {
  GCConfiguration conf;
  Target target;

  Map<String, Grid> maskArrays;
  static final Logger logger = LoggerFactory.getLogger(TargetHelper.class);

  public TargetHelper(GCConfiguration conf, Target target) {
    this.conf = conf;
    this.target = target;
  }

  public void initMaskArrays() {
    maskArrays = new HashMap<>();
    String zorder[] = null;

    if (conf.zorder != null)
      zorder = conf.zorder;
    if (target.zorder != null)
      zorder = target.zorder;

    if (zorder == null)
      return;

    Grid bake = Grids.empty(target.shape);

    float bakeArr[] = (float[]) bake.getSurface().getStorage();
    int size = bakeArr.length;

    for (String sname : zorder) {
      Source s = conf.getEmissionSource(sname);
      Grid originMask = s.getMaskArray();
      if (originMask == null)
        continue;

      Grid realMask = Grids.empty(target.shape);
      Grids.maskRegrid(originMask, realMask);
      maskArrays.put(sname, realMask);

      int cClip = 0; // cliped
      int cSet = 0; // setup.

      float maskArr[] = (float[]) realMask.getSurface().getStorage();
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
      logger.info(
        String.format("%s -- %s mask: %d/%d cells, %.2f%%/%.2f%% used, %.2f%%/%.2f%% clipped",
          target.name, sname,
          cSet, cSet + cClip, ((float) cSet) / (cSet + cClip) * 100, ((float) cSet) / bakeArr.length * 100,
          ((float) cClip) / (cSet + cClip) * 100, ((float) cClip) / bakeArr.length * 100));
    }
  }

  public Grid getMaskArray(String sn) {
    return maskArrays.get(sn);
  }

  public Target getTarget() {
    return target;
  }
}
