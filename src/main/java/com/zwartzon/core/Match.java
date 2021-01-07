package com.zwartzon.core;

import java.util.Optional;


class Match {
  final String competition_handle;
  final String home_club_handle;
  final String away_club_handle;
  final short day;

  private MatchStatus status = MatchStatus.SCHEDULED;
  private MatchResult result;

  Match(
      String competition_handle,
      String home_club_handle,
      String away_club_handle,
      short day
  ) {
    this.competition_handle = competition_handle;
    this.home_club_handle = home_club_handle;
    this.away_club_handle = away_club_handle;
    this.day = day;
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
