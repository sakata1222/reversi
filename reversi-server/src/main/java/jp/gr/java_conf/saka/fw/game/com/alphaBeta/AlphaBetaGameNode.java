package jp.gr.java_conf.saka.fw.game.com.alphaBeta;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.LongBinaryOperator;
import java.util.stream.Collectors;
import jp.gr.java_conf.saka.fw.game.base.GamePlayerColor;
import jp.gr.java_conf.saka.fw.game.base.IGame;
import jp.gr.java_conf.saka.fw.game.base.IGameMove;
import jp.gr.java_conf.saka.fw.game.base.IGameStatusEvaluateFunction;
import org.apache.commons.collections4.CollectionUtils;

class AlphaBetaGameNode<GAME extends IGame<MOVE>, MOVE extends IGameMove> {

  private GamePlayerColor playerColor;
  private GAME currentGame;
  private GamePlayerColor lastMoveColor;
  private MOVE lastMove;
  private IGameStatusEvaluateFunction<GAME> evaluateFunction;
  private List<AlphaBetaGameNode<GAME, MOVE>> children;
  private Long evaluationValue = null;
  private int nodeDepth;
  private long alphaValue;
  private long betaValue;

  static <GAME extends IGame<MOVE>, MOVE extends IGameMove> AlphaBetaGameNode<GAME, MOVE> root(
      GamePlayerColor playerColor, GAME currentGame, GamePlayerColor lastMoveColor,
      MOVE lastMove, IGameStatusEvaluateFunction<GAME> evaluateFunction) {
    return new AlphaBetaGameNode<>(playerColor, currentGame, lastMoveColor, lastMove,
        evaluateFunction, 0, Long.MIN_VALUE, Long.MAX_VALUE);
  }

  static <GAME extends IGame<MOVE>, MOVE extends IGameMove> AlphaBetaGameNode<GAME, MOVE> child(
      GamePlayerColor playerColor, GAME currentGame, GamePlayerColor lastMoveColor,
      MOVE lastMove, IGameStatusEvaluateFunction<GAME> evaluateFunction, int nodeDepth,
      long alphaValue,
      long betaValue) {
    return new AlphaBetaGameNode<>(playerColor, currentGame, lastMoveColor, lastMove,
        evaluateFunction, nodeDepth, alphaValue, betaValue);
  }

  AlphaBetaGameNode(GamePlayerColor playerColor, GAME currentGame, GamePlayerColor lastMoveColor,
      MOVE lastMove, IGameStatusEvaluateFunction<GAME> evaluateFunction, int nodeDepth,
      long alphaValue,
      long betaValue) {
    this.playerColor = playerColor;
    this.currentGame = currentGame;
    this.lastMoveColor = lastMoveColor;
    this.lastMove = lastMove;
    this.evaluateFunction = evaluateFunction;
    this.nodeDepth = nodeDepth;
    this.alphaValue = alphaValue;
    this.betaValue = betaValue;
  }

  Long getEvaluationValue() {
    return evaluationValue;
  }

  long expandChildren(int depth) {
    if (depth < 1) {
      // this node is leaf.
      evaluationValue = evaluateFunction.evaluate(playerColor, currentGame);
      return evaluationValue;
    }
    // depth >= 1
    {
      GamePlayerColor oppositeColor = lastMoveColor.nextPlayer();
      List<MOVE> oppositeColorPuttableMoves = currentGame.puttableMoves(oppositeColor);
      if (CollectionUtils.isNotEmpty(oppositeColorPuttableMoves)) {
        evaluationValue = expandChildren(oppositeColor, oppositeColorPuttableMoves, depth);
        return evaluationValue;
      }
    }
    {
      GamePlayerColor ownColor = lastMoveColor;
      List<MOVE> ownColorPuttableMoves = currentGame.puttableMoves(ownColor);
      if (CollectionUtils.isNotEmpty(ownColorPuttableMoves)) {
        evaluationValue = expandChildren(ownColor, ownColorPuttableMoves, depth);
        return evaluationValue;
      }
    }
    evaluationValue = evaluateFunction.evaluate(playerColor, currentGame);
    return evaluationValue;
  }

  private long expandChildren(GamePlayerColor moveColor,
      List<MOVE> moves, int depth) {
    children = new ArrayList<>();
    long childrenEvaluationValue = initialValue(moveColor);
    LongBinaryOperator reduceFunc = minOrMax(moveColor);
    for (MOVE move : moves) {
      @SuppressWarnings("unchecked")
      GAME clonedGame = (GAME) currentGame.clone();
      clonedGame.put(move, moveColor);
      AlphaBetaGameNode<GAME, MOVE> child = child(playerColor, clonedGame,
          moveColor, move, evaluateFunction, nodeDepth + 1, alphaValue, betaValue);
      children.add(child);
      // depth-first search
      long childEvaluationValue = child.expandChildren(depth - 1);
      childrenEvaluationValue = reduceFunc
          .applyAsLong(childrenEvaluationValue, childEvaluationValue);
      updateAlphaBeta(childrenEvaluationValue, moveColor);
      if (isCuttable(childrenEvaluationValue, moveColor)) {
        break;
      }
    }
    return childrenEvaluationValue;
  }

  private void updateAlphaBeta(long childEvaluationValue, GamePlayerColor childMoveColor) {
    if (playerColor.equals(childMoveColor)) {
      // max value
      alphaValue = childEvaluationValue;
    } else {
      // min node
      betaValue = childEvaluationValue;
    }
  }

  private boolean isCuttable(long childrenEvaluationValue, GamePlayerColor childMoveColor) {
    if (playerColor.equals(childMoveColor)) {
      // max node.
      return childrenEvaluationValue > betaValue; // beta cut
    } else {
      // min node
      return childrenEvaluationValue < alphaValue; // alpha cut
    }
  }

  List<AlphaBetaGameNode<GAME, MOVE>> getChildren() {
    return children;
  }

  MOVE getLastMove() {
    return lastMove;
  }

  Object debugInfo() {
    Map<String, Object> obj = new LinkedHashMap<>();
    obj.put("playerColor", playerColor);
    obj.put("lastMoveColor", lastMoveColor);
    obj.put("lastMove", lastMove);
    obj.put("evaluationValue", evaluationValue);
    obj.put("nodeDepth", nodeDepth);
    obj.put("alphaValue", alphaValue);
    obj.put("betaValue", betaValue);
    if (CollectionUtils.isNotEmpty(children)) {
      obj.put("children",
          children.stream().map(child -> child.debugInfo()).collect(Collectors.toList()));
    }
    return obj;
  }

  private LongBinaryOperator minOrMax(GamePlayerColor childMoveColor) {
    if (playerColor.equals(childMoveColor)) {
      return Math::max;
    } else {
      return Math::min;
    }
  }

  private long initialValue(GamePlayerColor childMoveColor) {
    if (playerColor.equals(childMoveColor)) {
      return Long.MIN_VALUE;
    } else {
      return Long.MAX_VALUE;
    }
  }
}
