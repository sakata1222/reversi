package jp.gr.java_conf.saka.fw.game.com.mcts;

import java.util.Objects;
import java.util.Optional;
import jp.gr.java_conf.saka.fw.game.base.GamePlayerColor;
import jp.gr.java_conf.saka.fw.game.base.IGame;
import jp.gr.java_conf.saka.fw.game.base.IGameMove;
import jp.gr.java_conf.saka.fw.game.com.mcts.select.IMctsNodeSelector;
import jp.gr.java_conf.saka.fw.game.com.mcts.select.impl.MctsNodeSelectorUctImpl;

public class MctsExecutorReuseImpl<GAME extends IGame<MOVE>, MOVE extends IGameMove> extends
    AbstractMctsExecutor<GAME, MOVE> {

  public static <GAME extends IGame<MOVE>, MOVE extends IGameMove> MctsExecutorReuseImpl<GAME, MOVE> newDefaultInstance(
      GamePlayerColor playerColor, int maxTotalTries,
      IMctsPlayOutExecutor<GAME, MOVE> playOutExecutor) {
    return new MctsExecutorReuseImpl<>(
        playerColor,
        maxTotalTries,
        MctsNodeSelectorUctImpl.newDefaultInstance(),
        playOutExecutor
    );
  }

  protected MctsExecutorReuseImpl(GamePlayerColor playerColor,
      int maxTotalTries,
      IMctsNodeSelector selector,
      IMctsPlayOutExecutor<GAME, MOVE> playOutExecutor) {
    super(playerColor, maxTotalTries, selector, playOutExecutor);
  }

  @Override
  protected MctsNode<GAME, MOVE> decideRoot(MctsNode<GAME, MOVE> previousNode, GAME game,
      GamePlayerColor playerColor) {
    if (Objects.isNull(previousNode)) {
      return MctsNode.root(game, playerColor);
    }
    if (previousNode.isSameState(game)) {
      // if the turn of the other player is skipped
      return previousNode;
    }
    Optional<MctsNode<GAME, MOVE>> current = previousNode.getChildren().stream()
        .filter(child -> child.isSameState(game)).findFirst();
    // reuse previous result when calculated.
    return current.orElseGet(() -> {
      // expected node is not expanded.
      return MctsNode.root(game, playerColor);
    });
  }
}
