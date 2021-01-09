package com.zwartzon.core;

import java.util.Optional;


class Match {
  final String handle;
  final String competition_handle;
  final String home_club_handle;
  final String away_club_handle;
  final short day;

  private MatchStatus status = MatchStatus.SCHEDULED;
  private MatchResult result;


  Match(
      String handle,
      String competition_handle,
      String home_club_handle,
      String away_club_handle,
      short day
  ) {
    this.handle = handle;
    this.competition_handle = competition_handle;
    this.home_club_handle = home_club_handle;
    this.away_club_handle = away_club_handle;
    this.day = day;
  }

  MatchStatus GetStatus() {
    return status;
  }

  void FinishMatch() {
    if (status == MatchStatus.SCHEDULED) {
      status = MatchStatus.FINISHED;
    }
  }

  void CancelMatch() {
    if (status == MatchStatus.SCHEDULED) {
      status = MatchStatus.CANCELLED;
    }
  }

  void SetResult(MatchResult result) {
    this.result = result;
  }

  Optional<MatchResult> GetResult() {
    return Optional.ofNullable(result);
  }
}


class MatchBuilder {
  String handle = "";
  String competition_handle = "";
  String home_club_handle = "";
  String away_club_handle = "";
  short day = 0;

  MatchBuilder WithHandle(String handle) {
    this.handle = handle;
    return this;
  }

  MatchBuilder WithCompetitionHandle(String competition_handle) {
    this.competition_handle = competition_handle;
    return this;
  }

  MatchBuilder WithHomeClubHandle(String home_club_handle) {
    this.home_club_handle = home_club_handle;
    return this;
  }

  MatchBuilder WithAwayClubHandle(String away_club_handle) {
    this.away_club_handle = away_club_handle;
    return this;
  }

  MatchBuilder WithDay(short day) {
    this.day = day;
    return this;
  }

  Match Build() {
    return new Match(
      handle, competition_handle, home_club_handle, away_club_handle, day
    );
  }
}
