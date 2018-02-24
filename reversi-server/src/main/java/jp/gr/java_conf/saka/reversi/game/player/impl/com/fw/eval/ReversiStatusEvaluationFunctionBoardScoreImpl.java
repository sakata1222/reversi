package jp.gr.java_conf.saka.reversi.game.player.impl.com.fw.eval;

import jp.gr.java_conf.saka.fw.game.base.GamePlayerColor;
import jp.gr.java_conf.saka.reversi.game.base.ReversiBoard;
import jp.gr.java_conf.saka.reversi.game.base.ReversiColor;
import jp.gr.java_conf.saka.reversi.game.player.impl.com.fw.IReversiStatusEvaluationFunction;
import jp.gr.java_conf.saka.reversi.game.player.impl.com.fw.ReversiColorDictionary;
import jp.gr.java_conf.saka.reversi.game.player.impl.com.fw.ReversiGameWrapper;

public class ReversiStatusEvaluationFunctionBoardScoreImpl implements
    IReversiStatusEvaluationFunction {

  /**
   * supported only 8 * 8
   */
  private static int[][] EVAL_TABLE =
      {
          {030, -12, 000, -01, -01, 000, -12, 030},
          {-12, -15, -03, -03, -03, -03, -15, -12},
          {000, -03, 000, -01, -01, 000, -03, 000},
          {-01, -03, -01, -01, -01, -01, -03, -01},
          {-01, -03, -01, -01, -01, -01, -03, -01},
          {000, -03, 000, -01, -01, 000, -03, 000},
          {-12, -15, -03, -03, -03, -03, -15, -12},
          {030, -12, 000, -01, -01, 000, -12, 030},
      };

  @Override
  public long evaluate(GamePlayerColor playerColor, ReversiGameWrapper game) {
    ReversiBoard board = game.getGame().getClonedBoard();

    long ownScore = eval(ReversiColorDictionary.resolve(playerColor), board);
    long againstScore = eval(ReversiColorDictionary.resolve(playerColor.nextPlayer()),
        board);
    return ownScore - againstScore;
  }

  private long eval(ReversiColor color, ReversiBoard board) {
    long value = 0;
    for (int i = 0; i < board.getSize(); i++) {
      for (int j = 0; j < board.getSize(); j++) {
        if (board.isSameColor(i, j, color)) {
          value += EVAL_TABLE[i][j];
        }
      }
    }
    return value;
  }
}
