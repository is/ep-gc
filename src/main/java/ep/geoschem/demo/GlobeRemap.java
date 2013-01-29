package ep.geoschem.demo;

import ep.common.PolarCoordinatesRegrid;

public class GlobeRemap {
  public static void DumpArray(Object o) {
    if (o instanceof float[]) {
      float[] arr = (float[]) o;

      for (int i = 0; i < arr.length; ++i) {
        System.out.println(i + ":" + arr[i]);
      }
      return;
    }

    if (o instanceof int[]) {
      int[] arr = (int[]) o;

      for (int i = 0; i < arr.length; ++i) {
        System.out.println(i + ":" + arr[i]);
      }
    }
  }


  public static void dumpCellSplitReference(PolarCoordinatesRegrid.CellSplitReference csr) {
    System.out.println("P:" + csr.P);

    System.out.println("Dest Cells:");
    DumpArray(csr.dCells);

    System.out.println("SourceRef:");
    DumpArray(csr.sRef);

    System.out.println("DestinationRef:");
    DumpArray(csr.dRef);

    System.out.println("SmallBorder:");
    DumpArray(csr.smallBorder);

    System.out.println("BigBorder:");
    DumpArray(csr.bigBorder);
  }


  public static void main(String args[]) {
    test1(3, 4);
    test1(3, 5);
    test1(6, 15);
    test1(10, 15);
    test1(15, 10);
    test1(360, 420);
    // test0(3, 4);
    // test0(4, 2);
    // test0(3, 9);
    // test0(9, 3);
    // test0(5, 7);
    PolarCoordinatesRegrid pcr = new PolarCoordinatesRegrid(
      new int[]{180, 360}, new int[]{360, 480});
    pcr.setup();
  }

  public static void test0(int n, int m) {
    System.out.println();
    System.out.println("--");
    System.out.println(n + " -> " + m);
    dumpCellSplitReference(PolarCoordinatesRegrid.buildCellSplitReference(n, m));
  }


  public static void test1(int n, int m) {
    System.out.println();
    System.out.println("--");

    PolarCoordinatesRegrid.CellSplitReference csr = PolarCoordinatesRegrid.buildCellSplitReference(n, m);
    System.out.println(n + " -> " + m + " = " + csr.P);
  }
}
