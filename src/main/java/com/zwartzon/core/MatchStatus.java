package com.zwartzon.core;


public enum MatchStatus {
  SCHEDULED(0), FINISHED(1), CANCELLED(-1);

  private int value;

  MatchStatus(int value) {
    this.value = value;
  }
}
