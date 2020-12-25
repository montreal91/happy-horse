package com.zwartzon.core;


import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

import java.util.Calendar;
import java.util.Date;

import org.junit.Test;


public class WorldTest {
  private static final int START_YEAR = 2077;
  private static final String HANDLE = "cyberpunk";

  @Test
  public void Initalization() {
    var w = new World(HANDLE, START_YEAR);
    assertEquals(w.handle, HANDLE);

    CompareCalendars(
      GetExpectedCalendar(START_YEAR, Calendar.FEBRUARY, 1),
      w.GetDate()
    );

  }

  @Test
  public void UpdateFunctions() {
    var w = new World(HANDLE, START_YEAR);

    w.NextDay();
    CompareCalendars(
      GetExpectedCalendar(START_YEAR, Calendar.FEBRUARY, 2),
      w.GetDate()
    );

    w.NextDay();
    CompareCalendars(
      GetExpectedCalendar(START_YEAR, Calendar.FEBRUARY, 3),
      w.GetDate()
    );

    w.NextYear();
    CompareCalendars(
      GetExpectedCalendar(START_YEAR + 1, Calendar.FEBRUARY, 7),
      w.GetDate()
    );

    w.NextDay();
    CompareCalendars(
      GetExpectedCalendar(START_YEAR + 1, Calendar.FEBRUARY, 8),
      w.GetDate()
    );

    w.NextYear();
    CompareCalendars(
      GetExpectedCalendar(START_YEAR + 2, Calendar.FEBRUARY, 6),
      w.GetDate()
    );

    w.NextDay();
    CompareCalendars(
      GetExpectedCalendar(START_YEAR + 2, Calendar.FEBRUARY, 7),
      w.GetDate()
    );
  }

  private static void CompareCalendars(Calendar expected, Calendar actual) {
    assertEquals(expected.get(Calendar.YEAR), actual.get(Calendar.YEAR));
    assertEquals(expected.get(Calendar.MONTH), actual.get(Calendar.MONTH));
    assertEquals(
      expected.get(Calendar.DAY_OF_MONTH),
      actual.get(Calendar.DAY_OF_MONTH)
    );
  }

  private static Calendar GetExpectedCalendar(int year, int month, int day) {
    var expected = Calendar.getInstance();
    expected.set(year, month, day);
    return expected;
  }
}
