package com.zwartzon.core;

import java.util.Calendar;
import java.util.Date;


public class World {
  public final String handle;

  private Calendar calendar;
  private boolean is_deleted = false;


  public World(String handle, int year) {
    this.handle = handle;
    InitCalendar(year);
  }

  public boolean IsDeleted() {
    return is_deleted;
  }

  public void DeleteWorld() {
    is_deleted = true;
  }

  public void NextDay() {
    calendar.add(Calendar.DATE, 1);
  }

  public void NextYear() {
    var y = calendar.get(Calendar.YEAR);
    SetStartOfSeason(y+1);
  }

  public Calendar GetDate() {
    var clone = Calendar.getInstance();
    clone.setTimeInMillis(calendar.getTimeInMillis());
    return clone;
  }

  private void InitCalendar(int year) {
    calendar = Calendar.getInstance();
    SetStartOfSeason(year);
  }

  private void SetStartOfSeason(int year) {
    // Each season starts on monday.
    calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
    calendar.set(Calendar.DAY_OF_WEEK_IN_MONTH, 1);
    calendar.set(Calendar.MONTH, Calendar.FEBRUARY);
    calendar.set(Calendar.YEAR, year);
  }
}
