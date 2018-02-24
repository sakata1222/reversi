package jp.gr.java_conf.saka.fw.game.com.alphaBeta;

import java.util.Comparator;
import java.util.Optional;
import jp.gr.java_conf.saka.fw.game.base.GamePlayerColor;
import jp.gr.java_conf.saka.fw.game.base.IGame;
import jp.gr.java_conf.saka.fw.game.base.IGameMove;
import jp.gr.java_conf.saka.fw.game.base.IGameStatusEvaluateFunction;

public class AlphaBetaExecutor<GAME extends IGame<MOVE>, MOVE extends IGameMove> implements
    IAlphaBetaExecutor<GAME, MOVE> {

  private GamePlayerColor playerColor;
  private IGameStatusEvaluateFunction<GAME> evaluateFunction;
  private Optional<Comparator<MOVE>> candidatesComparator;
  private int depth;
  private AlphaBetaGameNode<GAME, MOVE> root;

  public static <GAME extends IGame<MOVE>, MOVE extends IGameMove> AlphaBetaExecutor<GAME, MOVE> newDefaultInstance(
      GamePlayerColor playerColor,
      IGameStatusEvaluateFunction<GAME> evaluateFunction, int depth) {
    return new AlphaBetaExecutor<>(playerColor, evaluateFunction, Optional.empty(), depth);
  }

  public static <GAME extends IGame<MOVE>, MOVE extends IGameMove> AlphaBetaExecutor<GAME, MOVE> newInstanceWithCandidateComparator(
      GamePlayerColor playerColor,
      IGameStatusEvaluateFunction<GAME> evaluateFunction, Comparator<MOVE> candidatesComparator,
      int depth) {
    return new AlphaBetaExecutor<>(playerColor, evaluateFunction, Optional.of(candidatesComparator),
        depth);
  }

  public static <GAME extends IGame<MOVE>, MOVE extends IGameMove> AlphaBetaExecutor<GAME, MOVE> newInstanceWithCandidateComparator(
      GamePlayerColor playerColor,
      IGameStatusEvaluateFunction<GAME> evaluateFunction,
      Optional<Comparator<MOVE>> candidatesComparator,
      int depth) {
    return new AlphaBetaExecutor<>(playerColor, evaluateFunction, candidatesComparator, depth);
  }

  AlphaBetaExecutor(GamePlayerColor playerColor,
      IGameStatusEvaluateFunction<GAME> evaluateFunction,
      Optional<Comparator<MOVE>> candidatesComparator, int depth) {
    this.playerColor = playerColor;
    this.evaluateFunction = evaluateFunction;
    this.candidatesComparator = candidatesComparator;
    this.depth = depth;
  }

  @Override
  public MOVE execute(GAME game) {
    root = AlphaBetaGameNode.root(playerColor, game,
        playerColor.nextPlayer(), null, evaluateFunction, candidatesComparator);
    root.expandChildren(depth);
    return root.getChildren().stream()
        .sorted(Comparator.<AlphaBetaGameNode<GAME, MOVE>>comparingLong(
            AlphaBetaGameNode::getEvaluationValue)
            .reversed()).findFirst().map(AlphaBetaGameNode::getLastMove).get();
  }

  /**
   * for testing
   */
  AlphaBetaGameNode<GAME, MOVE> getRoot() {
    return root;
  }

  @SuppressWarnings("unused")
  private MOVE minMax(GAME game) {
    MinMaxGameNode<GAME, MOVE> root = new MinMaxGameNode<>(playerColor, game,
        playerColor.nextPlayer(), null, evaluateFunction);
    root.expandChildren(depth);
    root.calcEvaluationValue();
    return root.getChildren().stream()
        .sorted(Comparator.<MinMaxGameNode<?, ?>>comparingLong(MinMaxGameNode::calcEvaluationValue)
            .reversed()).findFirst().map(MinMaxGameNode::getLastMove).get();

  }
}
