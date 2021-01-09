package com.zwartzon.core;

import java.util.List;

// Basically, DAO (Data Access Object) is a repostory.
public interface MatchDao {
  List<Match> GetCompetitionDayMatches(String competion_handle, short day);
  List<Match> GetAllCompetitionMatches(String competion_handle);


  void Save(Match match);
  void SaveAll(List<Match> matches);
}
