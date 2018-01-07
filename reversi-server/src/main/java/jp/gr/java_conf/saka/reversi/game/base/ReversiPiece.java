package jp.gr.java_conf.saka.reversi.game.base;

public class ReversiPiece {

  private ReversiColor color;

  static ReversiPiece newPiece(ReversiColor color) {
    return new ReversiPiece(color);
  }

  ReversiPiece(ReversiColor color) {
    this.color = color;
  }

  ReversiColor getColor() {
    return color;
  }
}
