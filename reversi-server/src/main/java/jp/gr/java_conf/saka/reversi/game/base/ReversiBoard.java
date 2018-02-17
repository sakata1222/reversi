package jp.gr.java_conf.saka.reversi.game.base;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

public class ReversiBoard implements Cloneable {

  private int size;
  private ReversiPiece[][] pieces;

  static ReversiBoard newBoard(int size) {
    return new ReversiBoard(size);
  }

  ReversiBoard(int size) {
    this.size = size;
    this.pieces = new ReversiPiece[size][size];
  }

  boolean isEmpty(ReversiPosition position) {
    return isEmpty(position.getX(), position.getY());
  }

  boolean isEmpty(int x, int y) {
    return Objects.isNull(this.pieces[y][x]);
  }

  public boolean isInBoard(int x, int y) {
    return 0 <= x && x < size && 0 <= y && y < size;
  }

  boolean isSameColor(ReversiPosition position, ReversiColor color) {
    return isSameColor(position.getX(), position.getY(), color);
  }

  public boolean isSameColor(int x, int y, ReversiColor color) {
    throwExceptionIfOutOfBoard(x, y);
    return color.isSameColor(getColor(x, y));
  }

  boolean isOppositeColor(int x, int y, ReversiColor color) {
    throwExceptionIfOutOfBoard(x, y);
    return color.isOppositeColor(getColor(x, y));
  }

  ReversiColor getColor(ReversiPosition position) {
    return getColor(position.getX(), position.getY());
  }

  ReversiColor getColor(int x, int y) {
    return Optional.ofNullable(getPiece(x, y)).map(ReversiPiece::getColor).orElse(null);
  }

  public Optional<ReversiColor> getColorAsOptional(ReversiPosition position) {
    return getColorAsOptional(position.getX(), position.getY());
  }

  public Optional<ReversiColor> getColorAsOptional(int x, int y) {
    return getPieceAsOptional(x, y).map(p -> p.getColor());
  }

  ReversiPiece getPiece(int x, int y) {
    throwExceptionIfOutOfBoard(x, y);
    return this.pieces[y][x];
  }

  Optional<ReversiPiece> getPieceAsOptional(int x, int y) {
    return Optional.ofNullable(getPiece(x, y));
  }

  private void throwExceptionIfOutOfBoard(int x, int y) {
    if (!isInBoard(x, y)) {
      throw new IllegalArgumentException(x + ", " + y + " is out of board");
    }
  }

  private void throwExceptionIfOutOfBoard(ReversiPosition position) {
    throwExceptionIfOutOfBoard(position.getX(), position.getY());
  }

  void put(ReversiPosition position, ReversiColor color) {
    put(position.getX(), position.getY(), color);
  }

  /**
   * just put a piece.
   */
  void put(int x, int y, ReversiColor color) {
    throwExceptionIfOutOfBoard(x, y);
    if (!isEmpty(x, y)) {
      throw new IllegalArgumentException(x + ", " + y + " is not empty");
    }
    this.pieces[y][x] = ReversiPiece.newPiece(color);
  }

  void reverse(ReversiPosition position, ReversiColor color) {
    if (color.isOppositeColor(getColor(position))) {
      this.pieces[position.getY()][position.getX()] = ReversiPiece.newPiece(color);
    } else {
      throw new IllegalArgumentException(position + " is not reversible. color = " + color);
    }
  }

  public int getSize() {
    return size;
  }

  Map<ReversiColor, AtomicInteger> countNumOfPieces() {
    Map<ReversiColor, AtomicInteger> result = new HashMap<>();
    Arrays.stream(ReversiColor.values()).forEach(c -> result.put(c, new AtomicInteger(0)));
    for (int i = 0; i < size; i++) {
      for (int j = 0; j < size; j++) {
        if (Objects.nonNull(this.pieces[i][j])) {
          result.get(this.pieces[i][j].getColor()).incrementAndGet();
        }
      }
    }
    return result;
  }

  ReversiPiece[][] getPieces() {
    return pieces;
  }

  @Override
  public ReversiBoard clone() {
    try {
      ReversiBoard cloned = ReversiBoard.class.cast(super.clone());
      cloned.pieces = new ReversiPiece[size][];
      for (int i = 0; i < size; i++) {
        // ReversiPiece is immutable, so shallow copy is enough.
        cloned.pieces[i] = Arrays.copyOf(this.pieces[i], this.pieces[i].length);
      }
      return cloned;
    } catch (CloneNotSupportedException e) {
      throw new RuntimeException(e);
    }
  }

  String displayAsString() {
    StringBuilder builder = new StringBuilder();
    for (int i = 0; i < size; i++) {
      for (int j = 0; j < size; j++) {
        builder.append(//
            Optional.ofNullable(this.pieces[i][j]).map(p -> p.getColor().getShortString())
                .orElse(" "));
      }
      builder.append(System.lineSeparator());
    }
    return builder.toString();
  }

  public boolean isSameState(ReversiBoard otherBoard) {
    for (int i = 0; i < size; i++) {
      for (int j = 0; j < size; j++) {
        if (!Objects.equals(this.pieces[i][j], otherBoard.pieces[i][j])) {
          return false;
        }
      }
    }
    return true;
  }
}
