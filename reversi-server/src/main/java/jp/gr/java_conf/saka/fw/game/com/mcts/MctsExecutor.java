package jp.gr.java_conf.saka.fw.game.com.mcts;

import jp.gr.java_conf.saka.fw.game.base.GamePlayerColor;
import jp.gr.java_conf.saka.fw.game.com.mcts.IMctsPlayOutExecutor.PlayOutResult;
import jp.gr.java_conf.saka.fw.game.com.mcts.select.IMctsNodeSelector;
import jp.gr.java_conf.saka.fw.game.com.mcts.select.impl.MctsNodeSelectorUctImpl;
import org.apache.commons.lang3.mutable.MutableInt;

/**
 * Main logic of Monte Carlo Tree Search
 */
public class MctsExecutor<GAME extends IMctsGame<MOVE>, MOVE extends IMctsMove> {

  private GamePlayerColor playerColor;
  private int maxTotalTries;
  private int expandTriesThreshold;
  private IMctsNodeSelector selector;
  private IMctsPlayOutExecutor<GAME, MOVE> playOutExecutor;

  private MctsExecutor(GamePlayerColor playerColor,
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

  public static <GAME extends IMctsGame<MOVE>, MOVE extends IMctsMove> MctsExecutor<GAME, MOVE> newDefaultInstance(
      GamePlayerColor playerColor, int maxTotalTries,
      IMctsPlayOutExecutor<GAME, MOVE> playOutExecutor) {
    return new MctsExecutor<>(
        playerColor,
        maxTotalTries,
        /* 38 is chosen by http://www.kochi-tech.ac.jp/library/ron/pdf/2016/13/1170339.pdf */
        38,
        MctsNodeSelectorUctImpl.newDefaultInstance(),
        playOutExecutor
    );
  }

  public MOVE execute(GAME game) {
    MctsNode<GAME, MOVE> root = MctsNode.root(game, playerColor);
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
    return decided.getMove();
  }

  private int expandAndPlayOut(MctsNode<GAME, MOVE> target) {
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
