package ep.common;

public class PolarCoordinateRegrid {
  public static class FaceInfo {
    int shape[];

    int latRes;
    int lonRes;

    public FaceInfo(int shape[]) {
      this.shape = shape;
      this.latRes = shape[0];
      this.lonRes = shape[1];
    }
  }

  public static class CellSplitReference {
    public int P;

    public int mode;
    public int sRes;
    public int dRes;

    public int sRef[];
    public int dRef[];
    public int smallBorder[];
    public int bigBorder[];
    public int dCells[];
  }


  FaceInfo si, di;


  public PolarCoordinateRegrid(int srcShape[], int dstShape[]) {
    si = new FaceInfo(srcShape);
    di = new FaceInfo(dstShape);
  }

  public static CellSplitReference buildCellSplitReference(int sRes, int dRes) {
    int COMBINE_MODE = 1;
    int SPLIT_MODE = -1;

    int TAG_SRC = -1;
    int TAG_DST = 1;

    double sDiff = 1.0 / sRes;
    double dDiff = 1.0 / dRes;

    int mode;
    if (sRes > dRes) {
      mode = COMBINE_MODE;
    } else {
      mode = SPLIT_MODE;
    }

    double base = 0;
    double nextBase;
    double sBase = sDiff;
    double dBase = dDiff;

    int P = 0;
    int sP = 0;
    int dP = 0;

    int bigRes = Math.max(sRes, dRes);

    int sRef[] = new int[bigRes * 2];
    int dRef[] = new int[bigRes * 2];
    int smallBorder[] = new int[bigRes * 2];
    int bigBorder[] = new int[bigRes * 2];
    int dCells[] = new int[dRes];

    int border;
    if (mode == COMBINE_MODE) {
      border = TAG_SRC;
    } else {
      border = TAG_DST;
    }

    while (sP < sRes || dP < dRes) {
      System.out.println(
        "border=" + border + ", base=" + base + ", sbase=" +
          sBase + ", dbase=" + dBase + ", P=(" + P + "," + sP + "," + dP + ")");


      if (border == TAG_SRC) {
        nextBase = base + sDiff;
        if (nextBase <= dBase) {
          sRef[P] = sP;
          dRef[P] = dP;
          smallBorder[P] = border;
          dCells[dP] += 1;
          bigBorder[P] = TAG_SRC;
          border = TAG_SRC;

          ++P;
          ++sP;
          sBase += sDiff;
          base = nextBase;

          if (nextBase == dBase) {
            dBase += dDiff;
            ++dP;
          }
          continue;
        }

        sRef[P] = sP;
        dRef[P] = dP;
        smallBorder[P] = border;
        bigBorder[P] = TAG_DST;
        dCells[dP] += 1;

        border = TAG_DST;
        ++P;
        ++dP;
        base = dBase;
        dBase += dDiff;
      } else {

        nextBase = base + dDiff;
        if (nextBase <= sBase) {
          sRef[P] = sP;
          dRef[P] = dP;
          smallBorder[P] = border;
          bigBorder[P] = TAG_DST;
          dCells[dP] += 1;
          border = TAG_DST;

          ++P;
          ++dP;
          dBase += dDiff;
          base = nextBase;

          if (nextBase == sBase) {
            sBase += sDiff;
            ++sP;
          }
          continue;
        }

        sRef[P] = sP;
        dRef[P] = dP;
        smallBorder[P] = border;
        bigBorder[P] = TAG_SRC;
        dCells[dP] += 1;
        border = TAG_SRC;
        ++P;
        ++sP;
        base = sBase;
        sBase += sDiff;
      }
    }

    CellSplitReference csr = new CellSplitReference();
    csr.P = P;
    csr.sRes = sRes;
    csr.dRes = dRes;
    csr.mode = mode;
    csr.bigBorder = new int[P];
    csr.smallBorder = new int[P];
    csr.dRef = new int[P];
    csr.sRef = new int[P];
    csr.dCells = dCells;

    System.arraycopy(sRef, 0, csr.sRef, 0, P);
    System.arraycopy(dRef, 0, csr.dRef, 0, P);
    System.arraycopy(bigBorder, 0, csr.bigBorder, 0, P);
    System.arraycopy(smallBorder, 0, csr.smallBorder, 0, P);
    return csr;
  }
}
