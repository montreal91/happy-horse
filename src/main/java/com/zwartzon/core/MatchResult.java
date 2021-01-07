package com.zwartzon.core;


import java.util.List;
import java.util.StringJoiner;

import static com.zwartzon.core.Util.BoolToShort;


class MatchResult {
  private List<SetResult> set_results;

  MatchResult(final List<SetResult> set_results) {
    this.set_results = set_results;
  }

  short GetHomeSets() {
    short res = 0;
    for (var set : set_results) {
      res += BoolToShort(set.home_games > set.away_games);
    }
    return res;
  }

  short GetHomeGames() {
    short res = 0;
    for (var set : set_results) {
      res += set.home_games;
    }
    return res;
  }

  short GetAwaySets() {
    short res = 0;
    for (var set : set_results) {
      res += BoolToShort(set.away_games > set.home_games);
    }
    return res;
  }

  short GetAwayGames() {
    short res = 0;
    for (var set : set_results) {
      res += set.away_games;
    }
    return res;
  }

  @Override
  public String toString() {
    StringJoiner sb = new StringJoiner(" ");
    for (var set : set_results) {
      sb.add(set.toString());
    }
    return sb.toString();
  }
}
