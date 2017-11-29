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

  public int getX() {
    return x;
  }

  public int getY() {
    return y;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    ReversiPosition that = (ReversiPosition) o;

    if (x != that.x) {
      return false;
    }
    return y == that.y;
  }

  @Override
  public int hashCode() {
    int result = x;
    result = 31 * result + y;
    return result;
  }

  @Override
  public String toString() {
    return "ReversiPosition{" +
        "x=" + x +
        ", y=" + y +
        '}';
  }
}
