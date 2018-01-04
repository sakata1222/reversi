package jp.gr.java_conf.saka.reversi.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.IntUnaryOperator;
import java.util.stream.IntStream;

public class ReversiRule {

  static ReversiRule newInstance() {
    return new ReversiRule();
  }

  void initializeBoard(ReversiBoard board) {
    int size = board.getSize();
    board.put(size / 2 - 1, size / 2 - 1, ReversiColor.WHITE);
    board.put(size / 2, size / 2 - 1, ReversiColor.BLACK);
    board.put(size / 2 - 1, size / 2, ReversiColor.BLACK);
    board.put(size / 2, size / 2, ReversiColor.WHITE);
  }

  int put(ReversiBoard board, ReversiPosition position, ReversiColor color) {
    if (!board.isEmpty(position)) {
      throw new IllegalArgumentException(position + " is not empty.");
    }
    int reversed = reverseLineStream(board, position, color).sum();
    if (reversed == 0) {
      throw new IllegalArgumentException(position + " is not puttable");
    }
    board.put(position, color);
    return reversed;
  }

  private IntStream reverseLineStream(ReversiBoard board, ReversiPosition position,
      ReversiColor color) {
    IntUnaryOperator increment = i -> i + 1;
    IntUnaryOperator keep = i -> i;
    IntUnaryOperator decrement = i -> i - 1;
    return IntStream.of(
        reverseLine(board, position, color, decrement, decrement),
        reverseLine(board, position, color, decrement, keep),
        reverseLine(board, position, color, decrement, increment),
        reverseLine(board, position, color, keep, decrement),
        reverseLine(board, position, color, keep, increment),
        reverseLine(board, position, color, increment, decrement),
        reverseLine(board, position, color, increment, keep),
        reverseLine(board, position, color, increment, increment)//
    ).sequential();
    // do not use the parallel stream, because parallel stream is not effective this case.
  }

  private int reverseLine(ReversiBoard board, ReversiPosition putPosition, ReversiColor color,
      IntUnaryOperator xOperator, IntUnaryOperator yOperator) {
    int nextX = xOperator.applyAsInt(putPosition.getX());
    int nextY = yOperator.applyAsInt(putPosition.getY());
    boolean isNextToPutPoint = true;
    List<ReversiPosition> line = new ArrayList<>();
    while (board.isInBoard(nextX, nextY)) {
      Optional<ReversiColor> colorOpt = board.getColorAsOptional(nextX, nextY);
      if (isNextToPutPoint) {
        if (board.isOppositeColor(nextX, nextY, color)) {
          // OK. check next.
          line.add(ReversiPosition.xy(nextX, nextY));
          isNextToPutPoint = false;
        } else {
          // the point next to put point should be opposite color.
          return 0;
        }
      } else {
        if (board.isSameColor(nextX, nextY, color)) {
          // OK. reverse line.
          return reversePositions(board, line, color);
        } else if (board.isEmpty(nextX, nextY)) {
          // NG.
          return 0;
        } else {
          // if the point is opposite color, check next.
          line.add(ReversiPosition.xy(nextX, nextY));
        }
      }
      nextX = xOperator.applyAsInt(nextX);
      nextY = yOperator.applyAsInt(nextY);
    }
    return 0;
  }

  private int reversePositions(ReversiBoard board, List<ReversiPosition> positions,
      ReversiColor color) {
    positions.stream().forEach(pos -> board.reverse(pos, color));
    return positions.size();
  }

  boolean isPutaable(ReversiBoard board, ReversiPosition position, ReversiColor color) {
    if (!board.isEmpty(position)) {
      return false;
    }
    return reverseLineStream(board.clone(), position, color).anyMatch(reversed -> reversed > 0);
  }
}
