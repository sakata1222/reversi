package jp.gr.java_conf.saka.reversi.game.base;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import jp.gr.java_conf.saka.reversi.game.base.ReversiResult.ReversiResultBuilder;

public class ReversiGame implements Cloneable {

  private ReversiBoard board;
  private ReversiRule rule;

  public static ReversiGame newGame() {
    return new ReversiGame(ReversiBoard.newBoard(8), ReversiRule.newInstance()).init();
  }

  ReversiGame(ReversiBoard board, ReversiRule rule) {
    this.board = board;
    this.rule = rule;
  }

  ReversiGame init() {
    rule.initializeBoard(board);
    return this;
  }

  public ReversiResult getResult() {
    boolean isGameEnd = Arrays.stream(ReversiColor.values()).parallel()
        .noneMatch(color -> hasMove(color));
    ReversiResultBuilder builder = ReversiResult.builder();
    int size = board.getSize();
    for (int i = 0; i < size; i++) {
      for (int j = 0; j < size; j++) {
        board.getColorAsOptional(j, i).ifPresent(c -> builder.addPieceCount(c));
      }
    }
    if (isGameEnd) {
      builder.gameEnd();
    } else {
      builder.notGameEnd();
    }
    return builder.build();
  }

  public Map<ReversiColor, AtomicInteger> countNumOfPieces() {
    return this.board.countNumOfPieces();
  }

  public int getSize() {
    return this.board.getSize();
  }

  public boolean hasMove(ReversiColor color) {
    return !puttablePositions(color).isEmpty();
  }

  public void put(ReversiPosition pos, ReversiColor color) {
    rule.put(board, pos, color);
  }

  public List<ReversiPosition> puttablePositions(ReversiColor color) {
    int size = board.getSize();
    List<ReversiPosition> positions = new ArrayList<>();
    for (int i = 0; i < size; i++) {
      for (int j = 0; j < size; j++) {
        ReversiPosition position = ReversiPosition.xy(j, i);
        if (rule.isPutaable(board, position, color)) {
          positions.add(position);
        }
      }
    }
    return positions;
  }

  @Override
  public ReversiGame clone() {
    try {
      ReversiGame cloned = ReversiGame.class.cast(super.clone());
      cloned.board = this.board.clone();
      cloned.rule = this.rule; // rule is immutable
      return cloned;
    } catch (CloneNotSupportedException e) {
      throw new RuntimeException(e);
    }
  }

  public ReversiBoard getClonedBoard() {
    return board.clone();
  }

  public boolean isSameState(ReversiGame otherGame) {
    return this.board.isSameState(otherGame.board);
  }
}
