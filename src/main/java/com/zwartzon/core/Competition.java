package com.zwartzon.core;

import java.util.List;
import java.util.Optional;


interface Competition {
  boolean IsOver();
  boolean IsFinal();
  List<String> GetParticipants();
  void SetParticipants(List<String> participants);
  List<String> GetPromotedParticipants();
  Optional<String> GetWinner();
  List<CompetitionStandings> GetStandings();
  void Update();
}


abstract class CompetitionStandings {}
