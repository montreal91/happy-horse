package com.zwartzon.core;


public class Club {
  public final String handle;
  public final String world;
  private String owner = Owners.CPU;

  public Club(String handle, String world) {
    this.handle = handle;
    this.world = world;
  }

  public String GetOwner() {
    return this.owner;
  }

  public void SetOwner(String owner) {
    this.owner = owner;
  }
}


class Owners {
  final static String PLAYER = "plr";
  final static String CPU = "cpu";
}
