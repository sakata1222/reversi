package jp.gr.java_conf.saka.reversi.game;

import java.util.Objects;

public enum ReversiColor {
  BLACK("B"),

  WHITE("W");

  private String shortString;

  private ReversiColor(String shortString) {
    this.shortString = shortString;
  }

  boolean isSameColor(ReversiColor color) {
    return this == color;
  }

  boolean isOppositeColor(ReversiColor color) {
    return Objects.nonNull(color) && this != color;
  }

  public String getShortString() {
    return shortString;
  }
}
