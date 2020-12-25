package com.zwartzon;

import com.zwartzon.client.MainClient;
import com.zwartzon.infrastructure.CsvClubRepository;


public class App {
  public static void main(String[] args) {
    CsvSmokeTest();
    MainClient.Main(args);
  }

  private static void CsvSmokeTest() {
    var repo = new CsvClubRepository();
    var clubs = repo.GetBaseClubs("test");

    for (var club : clubs) {
      System.out.println(club.handle);
    }
  }
}
