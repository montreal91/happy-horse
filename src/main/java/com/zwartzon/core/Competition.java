package com.zwartzon.core;

import java.util.List;
import java.util.Optional;


interface Competition {
  boolean IsOver();
  boolean IsFinal();
  Optional<String> GetWinner();
  CompetitionStandings GetStandings();
  boolean CanUpdate();
  void Update();
}


abstract class CompetitionStandings {}
