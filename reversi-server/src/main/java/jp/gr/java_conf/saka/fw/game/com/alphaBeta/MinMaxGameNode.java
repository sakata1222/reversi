package jp.gr.java_conf.saka.fw.game.com.alphaBeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.LongBinaryOperator;
import jp.gr.java_conf.saka.fw.game.base.GamePlayerColor;
import jp.gr.java_conf.saka.fw.game.base.IGame;
import jp.gr.java_conf.saka.fw.game.base.IGameMove;
import jp.gr.java_conf.saka.fw.game.base.IGameStatusEvaluateFunction;
import org.apache.commons.collections4.CollectionUtils;

class MinMaxGameNode<GAME extends IGame<MOVE>, MOVE extends IGameMove> {

  private GamePlayerColor playerColor;
  private GAME currentGame;
  private GamePlayerColor lastMoveColor;
  private MOVE lastMove;
  private IGameStatusEvaluateFunction<GAME> evaluateFunction;
  private List<MinMaxGameNode<GAME, MOVE>> children;
  private Long evaluationValue = null;

  MinMaxGameNode(GamePlayerColor playerColor, GAME currentGame, GamePlayerColor lastMoveColor,
      MOVE lastMove, IGameStatusEvaluateFunction<GAME> evaluateFunction) {
    this.playerColor = playerColor;
    this.currentGame = currentGame;
    this.lastMoveColor = lastMoveColor;
    this.lastMove = lastMove;
    this.evaluateFunction = evaluateFunction;
  }

  boolean hasChild() {
    return CollectionUtils.isNotEmpty(children);
  }

  long calcEvaluationValue() {
    if (Objects.nonNull(evaluationValue)) {
      // already calculated
      return evaluationValue;
    }
    if (hasChild()) {
      GamePlayerColor childMoveColor = children.stream().map(
          child -> child.lastMoveColor).findFirst().get();
      LongBinaryOperator func = minOrMax(childMoveColor);
      // calculate evaluation value recursively
      evaluationValue = children.stream().mapToLong(child -> child.calcEvaluationValue())
          .reduce(func).getAsLong();
      return evaluationValue;
    } else {
      evaluationValue = evaluateFunction.evaluate(playerColor, currentGame);
      return evaluationValue;
    }
  }

  List<MinMaxGameNode<GAME, MOVE>> expandChildren(int depth) {
    if (depth < 1) {
      return null;
    }
    // depth >= 1
    {
      GamePlayerColor oppositeColor = lastMoveColor.nextPlayer();
      List<MOVE> oppositeColorPuttableMoves = currentGame.puttableMoves(oppositeColor);
      if (CollectionUtils.isNotEmpty(oppositeColorPuttableMoves)) {
        return expandChildren(oppositeColor, oppositeColorPuttableMoves, depth - 1);
      }
    }
    {
      GamePlayerColor ownColor = lastMoveColor;
      List<MOVE> ownColorPuttableMoves = currentGame.puttableMoves(ownColor);
      if (CollectionUtils.isNotEmpty(ownColorPuttableMoves)) {
        return expandChildren(ownColor, ownColorPuttableMoves, depth - 1);
      }
    }
    return children;
  }

  private List<MinMaxGameNode<GAME, MOVE>> expandChildren(GamePlayerColor moveColor,
      List<MOVE> moves, int depth) {
    children = new ArrayList<>();
    for (MOVE move : moves) {
      @SuppressWarnings("unchecked")
      GAME clonedGame = (GAME) currentGame.clone();
      clonedGame.put(move, moveColor);
      MinMaxGameNode<GAME, MOVE> child = new MinMaxGameNode<>(playerColor, clonedGame, moveColor,
          move, evaluateFunction);
      children.add(child);
      // depth-first search
      child.expandChildren(depth);
    }
    return children;
  }

  List<MinMaxGameNode<GAME, MOVE>> getChildren() {
    return children;
  }

  MOVE getLastMove() {
    return lastMove;
  }

  private LongBinaryOperator minOrMax(GamePlayerColor childMoveColor) {
    if (playerColor.equals(childMoveColor)) {
      return Math::max;
    } else {
      return Math::min;
    }
  }
}
