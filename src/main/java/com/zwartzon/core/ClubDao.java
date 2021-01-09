package com.zwartzon.core;

import java.util.List;


public interface ClubDao {
  List<Club> GetAllClubs(String game_world);
  Club GetClub(String game_world, String club_handle);
}
