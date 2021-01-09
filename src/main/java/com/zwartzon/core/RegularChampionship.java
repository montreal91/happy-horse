package com.zwartzon.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


class RegularChampionship implements Competition {
  private final String handle;
  private final MatchDao match_dao;
  private final MatchProcessor match_processor;
  private final List<String> participants;

  private boolean is_over = false;
  private short day = 0;


  RegularChampionship(
    String handle,
    MatchDao match_dao,
    MatchProcessor match_processor,
    List<String> participants
  ) {
    this.handle = handle;
    this.match_dao = match_dao;
    this.match_processor = match_processor;
    this.participants = participants;
  }

  @Override
  public boolean IsOver() {
    return is_over;
  }

  @Override
  public boolean IsFinal() {
    return false;
  }

  @Override
  public Optional<String> GetWinner() {
    // Stub, because we not concerned with winners yet.
    return Optional.ofNullable(null);
  }

  @Override
  public CompetitionStandings GetStandings() {
    var standings = new RegularChampionshipStandings(participants);
    for (var match : match_dao.GetAllCompetitionMatches(handle)) {
      standings.AddMatch(match);
    }
    return standings;
  }

  @Override
  public boolean CanUpdate() {
    return true;
  }

  @Override
  public void Update() {
    var matches = match_dao.GetCompetitionDayMatches(handle, day);
    for (var match : matches) {
      match.SetResult(match_processor.Process(match));
      match.FinishMatch();
    }
    match_dao.SaveAll(matches);
    CheckIfOver();
    day++;
  }

  private void CheckIfOver() {
    for (var match : match_dao.GetAllCompetitionMatches(handle)) {
      if (match.GetStatus().equals(MatchStatus.SCHEDULED)) {
        is_over = false;
        return;
      }
    }
    is_over = true;
  }
}


// Class with passive data
class StandingsRow {
  short sets_won = 0, games_won = 0, tiebreaks_won = 0;
}


// Immutable representation for passive data
class StandingsRowData {
  final short sets_won, games_won, tiebreaks_won;

  StandingsRowData(StandingsRow row) {
    sets_won = row.sets_won;
    games_won = row.games_won;
    tiebreaks_won = row.tiebreaks_won;
  }
}


class RegularChampionshipStandings extends CompetitionStandings {
  private Map<String, StandingsRow> result_map = new HashMap<>();

  RegularChampionshipStandings(List<String> participants) {
    result_map = new HashMap<>();
    for (var handle : participants) {
      result_map.put(handle, new StandingsRow());
    }
  }

  void AddMatch(Match match) {
    if (match.GetResult().isEmpty()) {
      return;
    }

    var result = match.GetResult().get();
    var home_row = result_map.get(match.home_club_handle);
    home_row.sets_won += result.GetHomeSets();
    home_row.games_won += result.GetHomeGames();

    var away_row = result_map.get(match.away_club_handle);
    away_row.sets_won += result.GetAwaySets();
    away_row.games_won += result.GetAwaySets();
  }

  List<StandingsRowData> GetRowData() {
    var result = new ArrayList<StandingsRowData>();
    for (var row : result_map.values()) {
      result.add(new StandingsRowData(row));
    }
    return result;
  }
}
