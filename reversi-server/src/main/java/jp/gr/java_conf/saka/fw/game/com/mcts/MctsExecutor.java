package jp.gr.java_conf.saka.fw.game.com.mcts;

import jp.gr.java_conf.saka.fw.game.base.GamePlayerColor;
import jp.gr.java_conf.saka.fw.game.base.IGame;
import jp.gr.java_conf.saka.fw.game.base.IGameMove;
import jp.gr.java_conf.saka.fw.game.com.mcts.select.IMctsNodeSelector;
import jp.gr.java_conf.saka.fw.game.com.mcts.select.impl.MctsNodeSelectorUctImpl;

/**
 * Main logic of Monte Carlo Tree Search
 */
public class MctsExecutor<GAME extends IGame<MOVE>, MOVE extends IGameMove> extends
    AbstractMctsExecutor<GAME, MOVE> {

  public static <GAME extends IGame<MOVE>, MOVE extends IGameMove> MctsExecutor<GAME, MOVE> newDefaultInstance(
      GamePlayerColor playerColor, int maxTotalTries,
      IMctsPlayOutExecutor<GAME, MOVE> playOutExecutor) {
    return new MctsExecutor<>(
        playerColor,
        maxTotalTries,
        MctsNodeSelectorUctImpl.newDefaultInstance(),
        playOutExecutor
    );
  }

  protected MctsExecutor(GamePlayerColor playerColor,
      int maxTotalTries,
      IMctsNodeSelector selector,
      IMctsPlayOutExecutor<GAME, MOVE> playOutExecutor) {
    super(playerColor, maxTotalTries, selector, playOutExecutor);
  }

  @Override
  protected MctsNode<GAME, MOVE> decideRoot(MctsNode<GAME, MOVE> previousNode, GAME game,
      GamePlayerColor playerColor) {
    // always create new node.
    return MctsNode.root(game, playerColor);
  }
}
