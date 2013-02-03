package ep.geoschem.demo;


import ep.common.ESID;
import org.stringtemplate.v4.ST;

public class STSample {
  public static void main(String args[]) {
    ST st0 = new ST("Hello, <name>");
    st0.add("name", "World");
    System.out.println(st0.render());

    ESID esid = new ESID("edgar", "2012", "a_b", "PM10");
    ST st1 = new ST("Hello, <es.name>");
    st1.add("es", esid);
    System.out.println(st1.render());
  }
}
