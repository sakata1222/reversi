package jp.gr.java_conf.saka.reversi.game.player.impl.com.fw.eval;

import java.util.concurrent.atomic.AtomicInteger;
import jp.gr.java_conf.saka.fw.game.base.GamePlayerColor;
import jp.gr.java_conf.saka.reversi.game.player.impl.com.fw.IReversiStatusEvaluationFunction;
import jp.gr.java_conf.saka.reversi.game.player.impl.com.fw.ReversiGameWrapper;

public class ReversiStatusEvaluationFunctionCustomImpl implements
    IReversiStatusEvaluationFunction {

  private static final AtomicInteger ZERO = new AtomicInteger(0);
  private ReversiStatusEvaluationFunctionNumOfFixedPieceImpl fixed = new ReversiStatusEvaluationFunctionNumOfFixedPieceImpl();
  private ReversiStatusEvaluationFunctionBoardScoreImpl score = new ReversiStatusEvaluationFunctionBoardScoreImpl();

  @Override
  public long evaluate(GamePlayerColor playerColor, ReversiGameWrapper game) {
    long fixedValue = fixed.evaluate(playerColor, game) * 100L;
    long scoreValue = score.evaluate(playerColor, game);
    return fixedValue + scoreValue;
  }
}
