package com.zwartzon.core;

import java.util.Optional;
import java.util.Date;


public class Match {
  public final String home_club_handle;
  public final String away_club_handle;
  public final Date date;

  private MatchStatus status = MatchStatus.SCHEDULED;
  private MatchResult result;

  public Match(
      String home_club_handle,
      String away_club_handle,
      Date date
  ) {
    this.home_club_handle = home_club_handle;
    this.away_club_handle = away_club_handle;
    this.date = date;
  }

  public void FinishMatch() {
    if (status == MatchStatus.SCHEDULED) {
      status = MatchStatus.FINISHED;
    }
  }

  public void CancelMatch() {
    if (status == MatchStatus.SCHEDULED) {
      status = MatchStatus.CANCELLED;
    }
  }

  public void SetResult(MatchResult result) {
    this.result = result;
  }

  public Optional<MatchResult> GetResult() {
    return Optional.ofNullable(result);
  }
}
