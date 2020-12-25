package com.zwartzon.core;

import java.util.StringJoiner;


public class MatchResultBuilder {
  public int home_sets = 0;
  public int away_sets = 0;
  public int home_games = 0;
  public int away_games = 0;
  public int home_tiebreaks = 0;
  public int away_tiebreaks = 0;
  public StringJoiner scoreboard = new StringJoiner(" ", "", "");

  MatchResultBuilder WithHomeSets(int home_sets) {
    this.home_sets = home_sets;
    return this;
  }

  MatchResultBuilder WithAwaySets(int away_sets) {
    this.away_sets = away_sets;
    return this;
  }
}