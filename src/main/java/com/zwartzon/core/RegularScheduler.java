package com.zwartzon.core;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


class RegularScheduler {
  private final MatchDao dao;

  private short starting_day = 2;
  private short recovery_day = 2;
  private short intragroup_rounds = 2;
  private short extragroup_rounds = 1;

  RegularScheduler(final MatchDao dao) {
    this.dao = dao;
  }

  void MakeScheduleForCompetition(
      String competition_handle, List<String> group1, List<String> group2
  ) {
    var raw_schedule = MakeCompleteSchedule(group1, group2);
    var day = starting_day;
    for (var day_pairs : raw_schedule) {
      BookDayMatches(day_pairs, day, competition_handle);
      day++;
      if (day % recovery_day == 0) {
        day++;
      }
    }
  }

  void SetRecoveryDay(short day) {
    recovery_day = day;
  }

  void SetIntraGroupRounds(short rounds) {
    if (rounds % 2 == 1) {
      return;
    }
    intragroup_rounds = rounds;
  }

  void SetExtraGroupRounds(short rounds) {
    if (rounds % 2 == 1) {
      return;
    }
  }

  void SetStartingDay(short starting_day) {
    this.starting_day = starting_day;
  }

  private void BookDayMatches(
    final List<ClubPair> day_pairs, short day, String competition_handle
  ) {
    var booked_matches = new ArrayList<Match>();
    MatchBuilder mb;
    for (var pair : day_pairs) {
      mb = new MatchBuilder();
      mb.WithCompetitionHandle(competition_handle).WithDay(day);
      mb.WithHomeClubHandle(pair.home).WithAwayClubHandle(pair.away);
      booked_matches.add(mb.Build());
    }
    dao.SaveAll(booked_matches);
  }

  private List<List<ClubPair>>
  MakeRounds(final List<List<ClubPair>> schedule, boolean in) {
    short rounds = extragroup_rounds;
    if (in) rounds  = intragroup_rounds;
    var result = new ArrayList<List<ClubPair>>();

    for (int i=0; i < rounds * 2; i++) {
      for (var day : schedule) {
        result.add(CopyDay(day, i % 2 == 0));
      }
    }
    return result;
  }

  private List<List<ClubPair>>
  MakeCompleteSchedule(final List<String> group1, final List<String> group2) {
    var g1_schedule = MakeBasicIntraGroupSchedule(group1);
    var g2_schedule = MakeBasicIntraGroupSchedule(group2);

    g1_schedule = MakeRounds(g1_schedule, true);
    g2_schedule = MakeRounds(g2_schedule, true);

    var final_schedule = MergeSchedules(g1_schedule, g2_schedule);

    var between = MakeBasicExtraGroupShcedule(group1, group2);
    between = MakeRounds(between, false);

    final_schedule.addAll(between);
    return final_schedule;
  }

  private static List<List<ClubPair>>
  MergeSchedules(
      final List<List<ClubPair>> schedule1,
      final List<List<ClubPair>> schedule2
  ) {
    var res = new ArrayList<List<ClubPair>>();
    List<ClubPair> tmp;
    for (int i=0; i<schedule1.size(); i++) {
      tmp = new ArrayList<>(schedule1.get(i));
      tmp.addAll(schedule2.get(i));
      res.add(tmp);
    }
    return res;
  }

  private static List<List<ClubPair>>
  MakeBasicIntraGroupSchedule(final List<String> clubs) {
    var gen = new DayListGenerator<String>(MakeEvenCopyOfGroup(clubs));
    var result = new ArrayList<List<ClubPair>>();
    while(gen.HasNext()) {
      result.add(MakeOneDay(gen.GetNext()));
    }
    return result;
  }

  private static List<List<ClubPair>>
  MakeBasicExtraGroupShcedule(
      final List<String> group1, final List<String> group2
  ) {
    var result = new ArrayList<List<ClubPair>>();
    var g2_cycle = new LinkedList<String>(group2);
    ArrayList<String> tmp;
    for (int i=0; i<group1.size(); i++) {
      tmp = new ArrayList(group1);
      tmp.addAll(g2_cycle);
      result.add(MakeOneDay(tmp));

      g2_cycle.addLast(g2_cycle.removeFirst());
    }
    return result;
  }

  private static List<String> MakeEvenCopyOfGroup(final List<String> clubs) {
    var res = new ArrayList<String>(clubs);
    if (res.size() % 2 == 1) {
      res.add(null);
    }
    return res;
  }

  private static List<ClubPair> MakeOneDay(final List<String> clubs) {
    var res = new ArrayList<ClubPair>();
    String home, away;
    for (int i=0; i<clubs.size() / 2; i++) {
      home = clubs.get(i);
      away = clubs.get(clubs.size() - 1 - i);
      if (home == null || away == null) {
        continue;
      }
      res.add(new ClubPair(home, away));
    }
    return res;
  }

  private static List<ClubPair> CopyDay(
      final List<ClubPair> day_pairs, boolean is_mirrored
  ) {
    var res = new ArrayList<ClubPair>();
    for (var pair : day_pairs) {
      if (!is_mirrored) {
        res.add(new ClubPair(pair));
      }
      else {
        res.add(ClubPair.Mirror(pair));
      }
    }
    return res;
  }
}


class DayListGenerator<T> {
  private List<T> inner;
  private int counter = 1;

  DayListGenerator(List<T> inner) {
    this.inner = inner;
  }

  List<T> GetNext() {
    var res = new ArrayList<T>(inner);
    Next();
    return res;
  }

  boolean HasNext() {
    return counter < inner.size();
  }

  private void Next() {
    var holder = inner.get(inner.size() - 1);
    T tmp;
    for (int i=1; i<inner.size(); i++) {
      tmp = inner.get(i);
      inner.set(i, holder);
      holder = tmp;
    }
    counter++;
  }
}


class ClubPair {
  final String home, away;

  ClubPair(String home, String away) {
    this.home = home;
    this.away = away;
  }

  ClubPair(final ClubPair pair) {
    this.home = pair.home;
    this.away = pair.away;
  }

  static ClubPair Mirror(ClubPair pair) {
    return new ClubPair(pair.away, pair.home);
  }
}
