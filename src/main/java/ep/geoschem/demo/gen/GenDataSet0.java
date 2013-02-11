package ep.geoschem.demo.gen;

import java.io.File;
import java.io.IOException;

import ucar.ma2.InvalidRangeException;

public class GenDataSet0 extends Generater {
  public static void main(String args[]) throws IOException, InvalidRangeException {
    GenDataSet0 g = new GenDataSet0();
    g.init(new File("conf/gen/ds0/gen.js"));
    g.gen();
  }
}
