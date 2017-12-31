package jp.gr.java_conf.saka.fw.game.base;

import java.util.Arrays;
import java.util.Objects;

public enum GamePlayerColor {
  FIRST,

  SECOND;

  public boolean isSameColor(GamePlayerColor color) {
    return this == color;
  }

  public boolean isOppositeColor(GamePlayerColor color) {
    return Objects.nonNull(color) && this != color;
  }

  public GamePlayerColor nextPlayer() {
    return Arrays.stream(values()).filter(this::isOppositeColor).findFirst().get();
  }

}
