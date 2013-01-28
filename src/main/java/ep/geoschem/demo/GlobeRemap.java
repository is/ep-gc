package ep.geoschem.demo;

import ep.common.PolarCoordinateRegrid;

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


  public static void dumpCellSplitReference(PolarCoordinateRegrid.CellSplitReference csr) {
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
    PolarCoordinateRegrid.CellSplitReference csr;

    System.out.println("3 -> 4");
    csr = PolarCoordinateRegrid.buildCellSplitReference(3, 4);
    dumpCellSplitReference(csr);

    System.out.println();
    System.out.println("--");

    System.out.println("4 -> 3");
    csr = PolarCoordinateRegrid.buildCellSplitReference(4, 3);
    dumpCellSplitReference(csr);

    System.out.println("4 -> 4");
    csr = PolarCoordinateRegrid.buildCellSplitReference(4, 4);
    dumpCellSplitReference(csr);

    System.out.println("4 -> 2");
    csr = PolarCoordinateRegrid.buildCellSplitReference(4, 2);
    dumpCellSplitReference(csr);
  }
}
