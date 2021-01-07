package com.zwartzon.core;


class SetResult {
  final short home_games;
  final short away_games;

  SetResult(short home_games, short away_games) {
    if (home_games == away_games) {
      // This should never happen, but just in case.
      home_games++;
    }
    this.home_games = home_games;
    this.away_games = away_games;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append(home_games).append(":").append(away_games);
    return sb.toString();
  }
}
