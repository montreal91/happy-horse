package com.zwartzon.infrastructure;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.zwartzon.core.Club;
import com.zwartzon.core.ClubRepository;


public class CsvClubRepository implements ClubRepository {
  public List<Club> GetBaseClubs(String game_world) {
    String path = "src/main/resources/data/clubs.csv";
    Scanner scanner;
    try {
      scanner = new Scanner(new File(path));
    }
    catch (FileNotFoundException e) {
      // System.out.println("File not found by path: " + path);
      return new ArrayList<Club>();
    }
    scanner.useDelimiter(";");

    // Skip title
    GetClub(scanner, game_world);
    // System.out.println("Skipped..... ok");
    var result = new ArrayList<Club>();
    while (scanner.hasNext()) {
      result.add(GetClub(scanner, game_world));
    }
    return result;
  }

  public Club GetClub(String game_world, String handle) {
    return null;
  }

  private Club GetClub(Scanner input, String game_world) {
    List<String> values = new ArrayList<>();
    for (int i=0; i<4; i++) {
      values.add(input.next());
    }
    // System.out.println("Processed..... " +
    //   values.get(0) + " " +
    //   values.get(1) + " " +
    //   values.get(2) + " " +
    //   values.get(3)
    // );
    return new Club(values.get(2), game_world);
  }
}
