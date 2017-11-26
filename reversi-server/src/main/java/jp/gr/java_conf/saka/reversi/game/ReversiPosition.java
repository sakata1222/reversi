package jp.gr.java_conf.saka.reversi.game;

public class ReversiPosition {

  private int x;
  private int y;

  public static ReversiPosition xy(int x, int y) {
    return new ReversiPosition(x, y);
  }

  ReversiPosition(int x, int y) {
    this.x = x;
    this.y = y;
  }

  int getX() {
    return x;
  }

  int getY() {
    return y;
  }

  @Override
  public String toString() {
    return "ReversiPosition{" +
        "x=" + x +
        ", y=" + y +
        '}';
  }
}
