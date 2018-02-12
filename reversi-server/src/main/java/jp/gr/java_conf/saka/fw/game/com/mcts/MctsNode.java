package jp.gr.java_conf.saka.fw.game.com.mcts;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import jp.gr.java_conf.saka.fw.game.base.GamePlayerColor;
import jp.gr.java_conf.saka.fw.game.base.IGame;
import jp.gr.java_conf.saka.fw.game.base.IGameMove;
import org.apache.commons.collections4.CollectionUtils;

/**
 * Node for Monte Carlo Tree Search
 */
public class MctsNode<GAME extends IGame<MOVE>, MOVE extends IGameMove> {

  private GAME game;
  private GamePlayerColor nextColor;
  private MOVE move;
  private MctsNode parent;
  private List<MctsNode<GAME, MOVE>> children;
  private MctsNodeResult result;

  static <GAME extends IGame<MOVE>, MOVE extends IGameMove> MctsNode<GAME, MOVE> root(GAME game,
      GamePlayerColor nextColor) {
    return new MctsNode<>(game, nextColor, null, null, new ArrayList<>(), new MctsNodeResult());
  }

  static <GAME extends IGame<MOVE>, MOVE extends IGameMove> MctsNode<GAME, MOVE> child(
      MctsNode parent,
      MOVE move, GamePlayerColor childColor) {
    @SuppressWarnings("unchecked")
    GAME clonedGame = (GAME) (parent.game.clone());
    clonedGame.put(move, childColor);
    return new MctsNode<>(clonedGame, childColor.nextPlayer(), move, parent, new ArrayList<>(),
        new MctsNodeResult());
  }

  private MctsNode(GAME game, GamePlayerColor nextColor,
      MOVE move, MctsNode parent,
      List<MctsNode<GAME, MOVE>> children, MctsNodeResult result) {
    this.game = game;
    this.nextColor = nextColor;
    this.move = move;
    this.parent = parent;
    this.children = children;
    this.result = result;
  }

  IGame getClonedGame() {
    return game.clone();
  }

  GamePlayerColor getNextColor() {
    return nextColor;
  }

  MOVE getMove() {
    return move;
  }

  public int getTotalTries() {
    return getResult().getTotal();
  }

  public double getWinRate() {
    return getResult().rate();
  }

  public List<MctsNode<GAME, MOVE>> getChildren() {
    return new ArrayList<>(children);
  }

  public List<MctsNode<GAME, MOVE>> expandNode() {
    if (hasChild()) {
      throw new IllegalStateException("This node has child already ");
    }
    if (expandNode(nextColor)) {
      // OK
      return this.getChildren();
    } else {
      // next color can not be put, so change to next player
      expandNode(nextColor.nextPlayer());
      return this.getChildren();
    }
  }

  private boolean expandNode(GamePlayerColor nextColor) {
    List<MOVE> candidates = game.puttableMoves(nextColor);
    if (CollectionUtils.isNotEmpty(candidates)) {
      candidates.forEach(candidate -> this.addChild(candidate, nextColor));
      return true;
    }
    return false;
  }

  private MctsNode addChild(MOVE move, GamePlayerColor childColor) {
    MctsNode<GAME, MOVE> child = MctsNode.child(this, move, childColor);
    children.add(child);
    return this;
  }

  private MctsNodeResult getResult() {
    return result;
  }

  void propagateResult(boolean isWon) {
    for (MctsNode node = this; Objects.nonNull(node); node = node.parent) {
      node.getResult().addResult(isWon);
    }
  }

  public boolean hasChild() {
    return CollectionUtils.isNotEmpty(children);
  }

  public boolean isSameState(GAME otherGame) {
    return this.game.isSameState(otherGame);
  }

  boolean overNumOfPlayOutThreshold(int threshold) {
    return getResult().getTotal() > threshold;
  }

  void markAsRoot() {
    this.parent = null;
  }
}
