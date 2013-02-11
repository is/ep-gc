package ep.geoschem.demo.gen;

import java.io.IOException;

import ucar.ma2.InvalidRangeException;

public class GenDataSet1 extends Generater {
  public static void main(String args[]) throws IOException, InvalidRangeException {
    GenDataSet1 g = new GenDataSet1();
    g.init("ds1");
    g.gen();
  }
}

