package ep.geoschem.demo;

import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;

public class SplitterSample {
  public static void main(String args[]) {
    String s = "S3, S4, S5, S6";
    String[] s2 = Iterables.toArray(
      Splitter.on(",").trimResults().split(s), String.class
    );

    System.out.println(s2.length);
    for (String s3: s2) {
      System.out.println("=" + s3 + "=");
    }
  }
}
