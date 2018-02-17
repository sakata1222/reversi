package jp.gr.java_conf.saka.reversi.game.player.impl.com.fw.eval;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import jp.gr.java_conf.saka.fw.game.base.GamePlayerColor;
import jp.gr.java_conf.saka.reversi.game.base.ReversiColor;
import jp.gr.java_conf.saka.reversi.game.player.impl.com.fw.IReversiStatusEvaluationFunction;
import jp.gr.java_conf.saka.reversi.game.player.impl.com.fw.ReversiColorDictionary;
import jp.gr.java_conf.saka.reversi.game.player.impl.com.fw.ReversiGameWrapper;

public class ReversiStatusEvaluationFunctionNumOfPieceImpl implements
    IReversiStatusEvaluationFunction {

  private static final AtomicInteger ZERO = new AtomicInteger(0);

  @Override
  public long evaluate(GamePlayerColor playerColor, ReversiGameWrapper game) {
    Map<ReversiColor, AtomicInteger> count = game.getGame().countNumOfPieces();
    int ownCount = count.getOrDefault(ReversiColorDictionary.resolve(playerColor), ZERO)
        .get();
    int againstCount = count.getOrDefault(//
        ReversiColorDictionary.resolve(playerColor.nextPlayer()), ZERO).get();
    return ownCount - againstCount;
  }
}
