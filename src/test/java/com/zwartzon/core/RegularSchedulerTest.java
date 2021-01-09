package com.zwartzon.core;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.junit.jupiter.api.Test;


public class RegularSchedulerTest {

  @Test
  public void CheckDayListGenerator() {
    var generator = new DayListGenerator<Integer>(
      Arrays.asList(1, 2, 3, 4, 5, 6)
    );
    var expected = GetExpectedOutput();

    var ind = 0;
    while (generator.HasNext()) {
      assertEquals(expected.get(ind), generator.GetNext());
      ind++;
    }
  }

  @Test
  public void CheckRegularScheduler() {
    var match_dao = new MockMatchDao();
    var scheduler = new RegularScheduler(match_dao);
    scheduler.SetRecoveryDay((short)3);

    var handle = "test_competition";
    var digits = Arrays.asList("1", "2", "3", "4", "5");
    var letters = Arrays.asList("A", "B", "C", "D", "E");
    scheduler.MakeScheduleForCompetition(handle, digits, letters);

    var validator = new RegularCompetitionValidator(
      digits, letters, (short)4, (short)2
    );

    for (var match : match_dao.GetAllCompetitionMatches(handle)) {
      validator.InsertMatch(match);
    }
    validator.Validate();
  }

  private static List<List<Integer>> GetExpectedOutput() {
    return Arrays.asList(
      Arrays.asList(1, 2, 3, 4, 5, 6),
      Arrays.asList(1, 6, 2, 3, 4, 5),
      Arrays.asList(1, 5, 6, 2, 3, 4),
      Arrays.asList(1, 4, 5, 6, 2, 3),
      Arrays.asList(1, 3, 4, 5, 6, 2)
    );
  }
}


class MockMatchDao implements MatchDao {
  private HashMap<Short, List<Match>> matches = new HashMap<>();

  @Override
  public List<Match> GetCompetitionDayMatches(
      String competion_handle, short day
  ) {
    return Collections.emptyList();
  }

  @Override
  public List<Match> GetAllCompetitionMatches(String competion_handle) {
    var res = new ArrayList<Match>();
    for (var day : matches.values()) {
      res.addAll(day);
    }
    return res;
  }

  @Override
  public void Save(Match match) {}

  @Override
  public void SaveAll(List<Match> matches) {
    for (var match : matches) {
      InsertMatch(match);
    }
  }

  private void InsertMatch(Match match) {
    var day = match.day;
    if (!matches.containsKey(day)) {
      matches.put(day, new ArrayList<Match>());
    }
    matches.get(day).add(match);
  }
}


class ClubMatchCounter {
  int home_matches = 0, away_matches = 0;
  int in_group = 0, cross_group = 0;

  int TotalMatches() {
    return home_matches + away_matches;
  }
}


class RegularCompetitionValidator {
  final List<String> group1, group2;
  final int in_group, cross_group;
  final HashMap<String, ClubMatchCounter> counters = new HashMap<>();
  final int expected_in_group, expected_cross_group;
  final int expected_home_matches;

  RegularCompetitionValidator(
      List<String> group1,
      List<String> group2,
      int in_group,
      int cross_group
  ) {
    assertEquals(group1.size(), group2.size());
    assertTrue(in_group % 2 == 0);
    assertTrue(cross_group % 2 == 0);
    this.group1 = group1;
    this.group2 = group2;
    this.in_group = in_group;
    this.cross_group = cross_group;
    InitCounters();

    expected_in_group = (group1.size() - 1) * in_group;
    expected_cross_group = group1.size() * cross_group;

    expected_home_matches = (expected_in_group + expected_cross_group) / 2;
  }

  void InsertMatch(Match match) {
    var home_counter = counters.get(match.home_club_handle);
    home_counter.home_matches++;
    var away_counter = counters.get(match.away_club_handle);
    away_counter.away_matches++;

    if (InGroupMatch(match)) {
      home_counter.in_group++;
      away_counter.in_group++;
    }
    else {
      home_counter.cross_group++;
      away_counter.cross_group++;
    }
  }

  void Validate() {
    for (var counter : counters.values()) {
      assertEquals(expected_home_matches, counter.home_matches);
      assertEquals(expected_home_matches, counter.away_matches);
      assertEquals(expected_in_group, counter.in_group);
      assertEquals(expected_cross_group, counter.cross_group);
    }
  }

  private void InitCounters() {
    for (var handle : group1) {
      counters.put(handle, new ClubMatchCounter());
    }
    for (var handle : group2) {
      counters.put(handle, new ClubMatchCounter());
    }
  }

  private boolean InGroupMatch(Match match) {
    if (group1.contains(match.home_club_handle) &&
        group1.contains(match.away_club_handle)
    ) {
      return true;
    }
    if (group2.contains(match.home_club_handle) &&
        group2.contains(match.away_club_handle)
    ) {
      return true;
    }
    return false;
  }
}
