package ep.geoschem.demo;


import ep.common.ESID;
import org.stringtemplate.v4.ST;


public class StringTemplateSample {
  public static void main(String args[]) {
    ST st0 = new ST("Hello, <name>");
    st0.add("name", "World");
    System.out.println(st0.render());

    ESID esid = new ESID("edgar", "2012", "PM10", "a_b");
    ST st1 = new ST("Hello, <es.name>");
    st1.add("es", esid);
    System.out.println(st1.render());

    ST st2 = new ST("Hello, <es.speciesLower>");
    st2.add("es", esid);
    System.out.println(st2.render());

    ST st3 = new ST("Hello, <name>");
    st3.add("name", ST.EMPTY_ATTR);

    ST st4 = new ST(st3);
    st4.add("name", "t4");
    System.out.println(st4.render());

    ST st5 = new ST(st3);
    st5.add("name", "t5");
    System.out.println(st5.render());

  }
}
