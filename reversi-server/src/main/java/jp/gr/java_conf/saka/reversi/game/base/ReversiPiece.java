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

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    ReversiPiece that = (ReversiPiece) o;

    return color == that.color;
  }

  @Override
  public int hashCode() {
    return color != null ? color.hashCode() : 0;
  }
}
