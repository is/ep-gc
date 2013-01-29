package ep.geoschem.demo;


public class DoubleDiv {
  public static void main(String[] args) {
    System.out.println(1.0 / 3 * 3);
    System.out.println(1.0 / 9 * 9);
    System.out.println(1.0 / 3);

    for (int i = 0; i <= 9; ++i) {
      System.out.println(1.0 / 9 * i);
    }

    double diff = 1.0 / 9;
    double obase = 0;
    for (int i = 0; i <= 9; ++i) {
      obase += diff;
      System.out.println(obase);
    }
  }
}
