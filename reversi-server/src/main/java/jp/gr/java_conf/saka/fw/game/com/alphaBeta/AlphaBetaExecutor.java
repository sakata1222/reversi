package jp.gr.java_conf.saka.fw.game.com.alphaBeta;

import java.util.Comparator;
import jp.gr.java_conf.saka.fw.game.base.GamePlayerColor;
import jp.gr.java_conf.saka.fw.game.base.IGame;
import jp.gr.java_conf.saka.fw.game.base.IGameMove;
import jp.gr.java_conf.saka.fw.game.base.IGameStatusEvaluateFunction;

public class AlphaBetaExecutor<GAME extends IGame<MOVE>, MOVE extends IGameMove> implements
    IAlphaBetaExecutor<GAME, MOVE> {

  private GamePlayerColor playerColor;
  private IGameStatusEvaluateFunction<GAME> evaluateFunction;
  private int depth;

  public static <GAME extends IGame<MOVE>, MOVE extends IGameMove> AlphaBetaExecutor<GAME, MOVE> newDefaultInstance(
      GamePlayerColor playerColor,
      IGameStatusEvaluateFunction<GAME> evaluateFunction, int depth) {
    return new AlphaBetaExecutor<>(playerColor, evaluateFunction, depth);
  }

  AlphaBetaExecutor(GamePlayerColor playerColor,
      IGameStatusEvaluateFunction<GAME> evaluateFunction, int depth) {
    this.playerColor = playerColor;
    this.evaluateFunction = evaluateFunction;
    this.depth = depth;
  }

  @Override
  public MOVE execute(GAME game) {
    MinMaxGameNode<GAME, MOVE> root = new MinMaxGameNode<>(playerColor, game,
        playerColor.nextPlayer(), null, evaluateFunction);
    root.expandChildren(depth);
    root.calcEvaluationValue();
    return root.getChildren().stream()
        .sorted(Comparator.<MinMaxGameNode<?, ?>>comparingLong(MinMaxGameNode::calcEvaluationValue)
            .reversed()).findFirst().map(MinMaxGameNode::getLastMove).get();
  }
}
