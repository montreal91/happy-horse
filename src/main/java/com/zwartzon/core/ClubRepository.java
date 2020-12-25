package com.zwartzon.core;

import java.util.List;


public interface ClubRepository {
  List<Club> GetBaseClubs(String game_world);
}
