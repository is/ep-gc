package ep.geoschem.demo.gen;

import java.io.IOException;

import ucar.ma2.InvalidRangeException;

public class GD1 extends Generater {
  public static void main(String args[]) throws IOException, InvalidRangeException {
    GD1 g = new GD1();
    g.init("1");
    g.gen();
  }
}

