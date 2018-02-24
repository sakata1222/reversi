package jp.gr.java_conf.saka.reversi.game.player.impl.com.alphaBeta;

import java.util.Comparator;
import java.util.function.ToIntFunction;
import jp.gr.java_conf.saka.reversi.game.base.ReversiPosition;
import jp.gr.java_conf.saka.reversi.game.player.impl.com.fw.GameReversiMove;

public class AlphaBetaReversiCandidateComparators {

  /**
   * @param posEvaluator if the function return greater value, the position select preferentially.
   */
  public static Comparator<GameReversiMove> newComparator(
      ToIntFunction<ReversiPosition> posEvaluator) {
    return Comparator.<GameReversiMove>comparingInt(
        move -> posEvaluator.applyAsInt(move.getPosition())).reversed();
  }

  public static Comparator<GameReversiMove> newDefaultComparator() {
    return newComparator(defaultPosEvaluator());
  }

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


  static ToIntFunction<ReversiPosition> defaultPosEvaluator() {
    return pos -> {
      return EVAL_TABLE[pos.getX()][pos.getY()];
    };
  }
}
