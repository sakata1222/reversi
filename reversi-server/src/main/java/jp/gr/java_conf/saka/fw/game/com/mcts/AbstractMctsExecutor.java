package jp.gr.java_conf.saka.fw.game.com.mcts;

import jp.gr.java_conf.saka.fw.game.base.GamePlayerColor;
import jp.gr.java_conf.saka.fw.game.base.IGame;
import jp.gr.java_conf.saka.fw.game.base.IGameMove;
import jp.gr.java_conf.saka.fw.game.com.mcts.IMctsPlayOutExecutor.PlayOutResult;
import jp.gr.java_conf.saka.fw.game.com.mcts.select.IMctsExecutor;
import jp.gr.java_conf.saka.fw.game.com.mcts.select.IMctsNodeSelector;
import org.apache.commons.lang3.mutable.MutableInt;

/**
 * Main logic of Monte Carlo Tree Search
 */
public abstract class AbstractMctsExecutor<GAME extends IGame<MOVE>, MOVE extends IGameMove> implements
    IMctsExecutor<GAME, MOVE> {

  private GamePlayerColor playerColor;
  private int maxTotalTries;
  private int expandTriesThreshold;
  private IMctsNodeSelector selector;
  private IMctsPlayOutExecutor<GAME, MOVE> playOutExecutor;
  private MctsNode<GAME, MOVE> previousNode;

  protected AbstractMctsExecutor(GamePlayerColor playerColor,
      int maxTotalTries,
      IMctsNodeSelector selector,
      IMctsPlayOutExecutor<GAME, MOVE> playOutExecutor) {
    this.playerColor = playerColor;
    this.maxTotalTries = maxTotalTries;
    /* 38 is chosen by http://www.kochi-tech.ac.jp/library/ron/pdf/2016/13/1170339.pdf */
    this.expandTriesThreshold = 38;
    this.selector = selector;
    this.playOutExecutor = playOutExecutor;
    this.previousNode = null;
  }

  protected AbstractMctsExecutor(GamePlayerColor playerColor,
      int maxTotalTries,
      int expandTriesThreshold,
      IMctsNodeSelector selector,
      IMctsPlayOutExecutor<GAME, MOVE> playOutExecutor) {
    this.playerColor = playerColor;
    this.maxTotalTries = maxTotalTries;
    this.expandTriesThreshold = expandTriesThreshold;
    this.selector = selector;
    this.playOutExecutor = playOutExecutor;
  }

  public MOVE execute(GAME game) {
    MctsNode<GAME, MOVE> root = decideRoot(previousNode, game, playerColor);
    root.markAsRoot();
    expandAndPlayOut(root);
    for (MutableInt i = new MutableInt(0); i.intValue() < maxTotalTries; i.increment()) {
      MctsNode<GAME, MOVE> candidate = selector.select(root.getChildren());
      if (candidate.overNumOfPlayOutThreshold(expandTriesThreshold)) {
        int numOfChild = expandAndPlayOut(candidate);
        i.add(Math.max(0, numOfChild - 1));
      } else {
        playOutAndPropagate(candidate);
      }
    }
    MctsNode<GAME, MOVE> decided = new MctsResultDecider().decide(root.getChildren());
    previousNode = decided;
    return decided.getMove();
  }

  protected abstract MctsNode<GAME, MOVE> decideRoot(MctsNode<GAME, MOVE> previousNode, GAME game,
      GamePlayerColor playerColor);

  private int expandAndPlayOut(MctsNode<GAME, MOVE> target) {
    if (target.hasChild()) {
      return 0;
    }
    target.expandNode().stream().parallel().forEach(this::playOutAndPropagate);
    return target.getChildren().size();
  }

  private void playOutAndPropagate(MctsNode<GAME, MOVE> target) {
    @SuppressWarnings("unchecked")
    GAME clonedGame = (GAME) target.getClonedGame();
    PlayOutResult result = playOutExecutor.playOut(clonedGame, target.getNextColor());
    target.propagateResult(result.isWon());
  }
}
