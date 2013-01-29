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
  CellSplitReference latCSR, lonCSR;

  double latLimitPointTable[];
  double lonLimitPointTable[];

  double sLatFactor[];
  double sLonFactor[];
  double dLatFactor[];
  double dLonFactor[];
  double cellLatFactor[];
  double cellLonFactor[];


  public PolarCoordinateRegrid(int srcShape[], int dstShape[]) {
    si = new FaceInfo(srcShape);
    di = new FaceInfo(dstShape);
  }


//  public void check() {
//    for (int i = 0; i < latCSR.P; ++i) {
//      if (cellLatFactor[i] <= 0) {
//        System.out.println("error-lat " + i + ":" + cellLatFactor[i]);
//      } else {
//        System.out.println(i + " = " + cellLatFactor[i]);
//      }
//    }
//
//    for (int i = 0; i < lonCSR.P; ++i) {
//      if (cellLonFactor[i] <= 0) {
//        System.out.println("error-lat " + i + ":" + cellLonFactor[i] + "/" +  lonCSR.sRef[i] + "-" + lonCSR.dRef[i]);
//      } else {
//        System.out.println(i + " = " + cellLonFactor[i]);
//      }
//    }
//  }


  public void setup() {
    latCSR = buildCellSplitReference(si.latRes, di.latRes);
    lonCSR = buildCellSplitReference(si.lonRes, di.lonRes);
    buildLimitPointTable();
    buildFactorTables();
  }


  void buildFactorTables() {
    sLatFactor = new double[si.latRes];
    sLonFactor = new double[si.lonRes];
    dLatFactor = new double[di.latRes];
    dLonFactor = new double[di.lonRes];

    // cellLatFactor = new double[latCSR.P];
    // cellLonFactor = new double[lonCSR.P];

    int i;
    int offset;

    for (i = 0; i < si.latRes; ++i) {
      sLatFactor[i] = latLimitPointTable[i + 1] - latLimitPointTable[i];
    }
    for (i = 0; i < si.lonRes; ++i) {
      sLonFactor[i] = lonLimitPointTable[i + 1] - lonLimitPointTable[i];
    }

    for (i = 0, offset = si.latRes + 1; i < di.latRes; ++i, ++offset) {
      dLatFactor[i] = latLimitPointTable[offset + 1] - latLimitPointTable[offset];
    }

    for (i = 0, offset = si.lonRes + 1; i < di.lonRes; ++i, ++offset) {
      dLonFactor[i] = lonLimitPointTable[offset + 1] - lonLimitPointTable[offset];
    }

    cellLatFactor = buildCellFactorTable(latLimitPointTable, latCSR);
    cellLonFactor = buildCellFactorTable(lonLimitPointTable, lonCSR);
  }


  static double[] buildCellFactorTable(double pointTable[], CellSplitReference csr) {
    double factors[] = new double[csr.P];
    for (int i = 0; i < csr.P; ++i) {
      factors[i] =
        pointTable[getCellLimitPointIndex(csr.sRes, csr.sRef[i], csr.dRef[i], csr.bigBorder[i], false)] -
          pointTable[getCellLimitPointIndex(csr.sRes, csr.sRef[i], csr.dRef[i], csr.smallBorder[i], true)];
    }
    return factors;
  }


  static int getCellLimitPointIndex(int sRes, int sindex, int dindex, int border, boolean small) {
    int realindex;

    if (border == -1) {
      realindex = sindex;
    } else {
      realindex = sRes + 1 + dindex;
    }
    if (!small)
      realindex += 1;

    return realindex;
  }


  protected void buildLimitPointTable() {
    latLimitPointTable = new double[si.latRes + di.latRes + 2];
    lonLimitPointTable = new double[si.lonRes + di.lonRes + 2];

    for (int i = 0; i <= si.latRes; ++i) {
      latLimitPointTable[i] = Math.sin(Math.PI / si.latRes * i - Math.PI / 2);
    }

    for (int i = 0; i <= di.latRes; ++i) {
      latLimitPointTable[si.latRes + 1 + i] = Math.sin(Math.PI / di.latRes * i - Math.PI / 2);
    }

    for (int i = 0; i <= si.lonRes; ++i) {
      // lonLimitPointTable[i] = 2 * Math.PI / si.lonRes * i;
      lonLimitPointTable[i] = di.lonRes * i;
    }

    for (int i = 0; i <= di.lonRes; ++i) {
      // lonLimitPointTable[si.lonRes + 1 + i] = 2 * Math.PI / di.lonRes * i;
      lonLimitPointTable[si.lonRes + 1 + i] = si.lonRes * i;
    }
  }

  public static CellSplitReference buildCellSplitReference(int sRes, int dRes) {
    int COMBINE_MODE = 1;
    int SPLIT_MODE = -1;

    int TAG_SRC = -1;
    int TAG_DST = 1;

    int mode;
    if (sRes > dRes) {
      mode = COMBINE_MODE;
    } else {
      mode = SPLIT_MODE;
    }


    long nextBase;
    long sDiff = dRes;
    long dDiff = sRes;
    long sBase = sDiff;
    long dBase = dDiff;

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
//      System.out.println(
//        "border=" + border + ", base=" + base + ", sbase=" +
//          sBase + ", dbase=" + dBase + ", P=(" + P + "," + sP + "," + dP + ")");

      if (border == TAG_SRC) {
        nextBase = sDiff * (sP + 1);

        if (nextBase <= dBase) {
          sRef[P] = sP;
          dRef[P] = dP;
          smallBorder[P] = border;
          dCells[dP] += 1;
          bigBorder[P] = TAG_SRC;
          border = TAG_SRC;

          ++P;
          ++sP;
          //sBase = sDiff * (sP + 1);
          sBase += sDiff;
          if (nextBase == dBase) {
            ++dP;
            //dBase = dDiff * (dP + 1);
            dBase += dDiff;
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
        //dBase = dDiff *(dP + 1);
        dBase += dDiff;
      } else {
        nextBase = dDiff * (dP + 1);
        if (nextBase <= sBase) {
          sRef[P] = sP;
          dRef[P] = dP;
          smallBorder[P] = border;
          bigBorder[P] = TAG_DST;
          dCells[dP] += 1;
          border = TAG_DST;

          ++P;
          ++dP;
          //dBase = dDiff * (dP + 1);
          dBase += dDiff;

          if (nextBase == sBase) {
            ++sP;
            //sBase = sDiff * (sP + 1);
            sBase += sDiff;
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
        //sBase = sDiff * (sP + 1);
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
