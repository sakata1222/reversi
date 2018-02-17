package jp.gr.java_conf.saka.reversi.game.player.impl.com.fw.eval;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.IntUnaryOperator;
import jp.gr.java_conf.saka.fw.game.base.GamePlayerColor;
import jp.gr.java_conf.saka.reversi.game.base.ReversiBoard;
import jp.gr.java_conf.saka.reversi.game.base.ReversiColor;
import jp.gr.java_conf.saka.reversi.game.base.ReversiPosition;
import jp.gr.java_conf.saka.reversi.game.player.impl.com.fw.IReversiStatusEvaluationFunction;
import jp.gr.java_conf.saka.reversi.game.player.impl.com.fw.ReversiColorDictionary;
import jp.gr.java_conf.saka.reversi.game.player.impl.com.fw.ReversiGameWrapper;

public class ReversiStatusEvaluationFunctionNumOfFixedPieceImpl implements
    IReversiStatusEvaluationFunction {

  private static final AtomicInteger ZERO = new AtomicInteger(0);

  @Override
  public long evaluate(GamePlayerColor playerColor, ReversiGameWrapper game) {
    ReversiBoard board = game.getGame().getClonedBoard();
    // this count is not rigorous.
    long ownCount = count(ReversiColorDictionary.resolve(playerColor), board);
    long againstCount = count(ReversiColorDictionary.resolve(playerColor.nextPlayer()),
        board);
    return ownCount - againstCount;
  }

  private long count(ReversiColor color, ReversiBoard board) {
    Set<ReversiPosition> fixed = new LinkedHashSet<>();
    int edge = board.getSize() - 1;
    {
      // left top
      // left top to left bottom
      fixed.addAll(lineSearch(color, board, ReversiPosition.xy(0, 0),//
          x -> x, y -> y + 1
      ));
      // left top to right top
      fixed.addAll(lineSearch(color, board, ReversiPosition.xy(0, 0),//
          x -> x + 1, y -> y
      ));
      // left top to right bottom
      fixed.addAll(lineSearch(color, board, ReversiPosition.xy(0, 0),//
          x -> x + 1, y -> y + 1
      ));
    }
    {
      // right top
      // right top to right bottom
      fixed.addAll(lineSearch(color, board, ReversiPosition.xy(edge, 0),//
          x -> x, y -> y + 1
      ));
      // right top to left top
      fixed.addAll(lineSearch(color, board, ReversiPosition.xy(edge, 0),//
          x -> x - 1, y -> y
      ));
      // right top to left bottom
      fixed.addAll(lineSearch(color, board, ReversiPosition.xy(edge, 0),//
          x -> x - 1, y -> y + 1
      ));
    }
    {
      // left bottom
      // left bottom to left top
      fixed.addAll(lineSearch(color, board, ReversiPosition.xy(0, edge),//
          x -> x, y -> y - 1
      ));
      // left bottom to right bottom
      fixed.addAll(lineSearch(color, board, ReversiPosition.xy(0, edge),//
          x -> x + 1, y -> y
      ));
      // left bottom to right top
      fixed.addAll(lineSearch(color, board, ReversiPosition.xy(0, edge),//
          x -> x + 1, y -> y - 1
      ));
    }
    {
      // right bottom
      // right bottom to right top
      fixed.addAll(lineSearch(color, board, ReversiPosition.xy(edge, edge),//
          x -> x, y -> y - 1
      ));
      // right bottom to left bottom
      fixed.addAll(lineSearch(color, board, ReversiPosition.xy(edge, edge),//
          x -> x - 1, y -> y
      ));
      // right bottom to left top
      fixed.addAll(lineSearch(color, board, ReversiPosition.xy(edge, edge),//
          x -> x - 1, y -> y - 1
      ));
    }
    return fixed.size();
  }

  private Set<ReversiPosition> lineSearch(ReversiColor color, ReversiBoard board,
      ReversiPosition base,
      IntUnaryOperator xOperator, IntUnaryOperator yOperator) {
    Set<ReversiPosition> fixed = new LinkedHashSet<>();
    if (board.isSameColor(base.getX(), base.getY(), color)) {
      fixed.add(base);
      int nextX = xOperator.applyAsInt(base.getX());
      int nextY = yOperator.applyAsInt(base.getY());
      while (board.isInBoard(nextX, nextY)) {
        if (board.isSameColor(nextX, nextY, color)) {
          fixed.add(ReversiPosition.xy(nextX, nextY));
        } else {
          break;
        }
        nextX = xOperator.applyAsInt(nextX);
        nextY = yOperator.applyAsInt(nextY);
      }
    }
    return fixed;
  }
}
