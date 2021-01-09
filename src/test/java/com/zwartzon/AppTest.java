package com.zwartzon;


import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;

import org.junit.jupiter.api.Test;


public class AppTest {
  @Test
  public void CheckCalendarMap() {
    var calendar_map = new HashMap<Calendar, String>();

    var cal1 = Calendar.getInstance();
    var cal2 = Calendar.getInstance();
    var cal3 = Calendar.getInstance();
    cal1.set(2077, 1, 1);
    cal2.set(2077, 1, 1);
    cal3.set(2077, 1, 2);

    calendar_map.put(cal1, "foo");
    assertEquals(cal1.get(Calendar.DAY_OF_YEAR), cal2.get(Calendar.DAY_OF_YEAR));

    cal1.add(Calendar.DATE, 1);
    assertEquals(cal1.get(Calendar.DAY_OF_YEAR), cal3.get(Calendar.DAY_OF_YEAR));
  }

  @Test
  public void CheckLinkedList() {
    var ll = new LinkedList<Integer>();
    ll.addLast(1);
    ll.addLast(2);
    ll.addLast(3);
    ll.addLast(4);
    assertEquals(Arrays.asList(1, 2, 3, 4), ll);

    ll.addLast(ll.removeFirst());
    assertEquals(Arrays.asList(2, 3, 4, 1), ll);

    ll.addLast(ll.removeFirst());
    assertEquals(Arrays.asList(3, 4, 1, 2), ll);
  }
}
