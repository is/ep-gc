package ep.common;

import java.util.Iterator;

public class DateRange implements Iterable<String> {
  String begin, end;
  String dateStep;

  public static class DateRangeIterator implements Iterator<String> {
    DateRange range;
    String cur;
    String end;

    public DateRangeIterator(DateRange range) {
      this.range = range;
      cur = range.begin;
      end = incrDate(range.end);
    }


    @Override
    public boolean hasNext() {
      if (cur.equals(end))
        return false;
      return true;
    }

    @Override
    public String next() {
      String res = cur;
      cur = incrDate(cur);
      return res;
    }

    @Override
    public void remove() {
      next();
    }
  }

  public DateRange(String begin, String end) {
    this.begin = begin;
    this.end = end;

    if (begin.length() == 6)
      dateStep = "monthly";
    else
      dateStep = "yearly";
  }

  @Override
  public Iterator<String> iterator() {
    return new DateRangeIterator(this);
  }


  public static String incrDate(String date) {
    if (date.length() == 4) {
      return String.format("%04d", Integer.parseInt(date) + 1);
    }

    int year = Integer.parseInt(date.substring(0, 4));
    int month = Integer.parseInt(date.substring(4, 6));

    month += 1;
    if (month == 13) {
      year += 1;
      month = 1;
    }

    return String.format("%04d%02d", year, month);
  }
}
