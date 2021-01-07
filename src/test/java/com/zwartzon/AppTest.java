package com.zwartzon;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Date;
import java.util.Calendar;

import org.junit.Test;


public class AppTest {
  @Test
  public void checkCalendarMap() {
    var calendar_map = new HashMap<Calendar, String>();

    var cal1 = Calendar.getInstance();
    var cal2 = Calendar.getInstance();
    var cal3 = Calendar.getInstance();
    cal1.set(2077, 1, 1);
    cal2.set(2077, 1, 1);
    cal3.set(2077, 1, 2);
    System.out.println(cal1);

    calendar_map.put(cal1, "foo");
    assertEquals(cal1.get(Calendar.DAY_OF_YEAR), cal2.get(Calendar.DAY_OF_YEAR));

    cal1.add(Calendar.DATE, 1);
    assertEquals(cal1.get(Calendar.DAY_OF_YEAR), cal3.get(Calendar.DAY_OF_YEAR));
  }
}
