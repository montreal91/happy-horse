package com.zwartzon.core;


public class MatchResult {
  public final int home_sets;
  public final int away_sets;
  public final int home_games;
  public final int away_games;
  public final int home_tiebreaks;
  public final int away_tiebreaks;
  public final String scoreboard;

  public MatchResult(
      int home_sets,
      int away_sets,
      int home_games,
      int away_games,
      int home_tiebreaks,
      int away_tiebreaks,
      String scoreboard
  ) {

    this.home_sets = home_sets;
    this.away_sets = away_sets;
    this.home_games = home_games;
    this.away_games = away_games;
    this.home_tiebreaks = home_tiebreaks;
    this.away_tiebreaks = away_tiebreaks;
    this.scoreboard = scoreboard;
  }
}
