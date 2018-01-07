package jp.gr.java_conf.saka.reversi.game.player.impl.com.mcts;

import jp.gr.java_conf.saka.fw.game.com.mcts.MctsExecutorReuseImpl;
import jp.gr.java_conf.saka.fw.game.com.mcts.select.IMctsExecutor;
import jp.gr.java_conf.saka.reversi.game.base.IReadOnlyReversiContext;
import jp.gr.java_conf.saka.reversi.game.base.ReversiColor;
import jp.gr.java_conf.saka.reversi.game.base.ReversiPosition;
import jp.gr.java_conf.saka.reversi.game.player.IReversiPlayer;


public class ReversiMctsReusePlayer implements IReversiPlayer {

  private ReversiColor playerColor;
  private int maxTotalTries;
  private IMctsExecutor<MctsReversiGame, MctsReversiMove> executor;

  public static ReversiMctsReusePlayer mctsReusePlayer(int maxTotalTries) {
    return new ReversiMctsReusePlayer(maxTotalTries);
  }

  ReversiMctsReusePlayer(int maxTotalTries) {
    this.maxTotalTries = maxTotalTries;
  }

  @Override
  public String type() {
    return "MCTS Reuse Player(" + maxTotalTries + ")";
  }

  @Override
  public IReversiPlayer init(ReversiColor playerColor) {
    this.playerColor = playerColor;
    executor = MctsExecutorReuseImpl.newDefaultInstance(
        MctsReversiColorDictionary.resolve(playerColor),
        maxTotalTries,
        new MctsReversiRandomPlayOutExecutor(playerColor));
    return this;
  }

  @Override
  public ReversiPosition think(IReadOnlyReversiContext context) {
    MctsReversiMove move = executor.execute(MctsReversiGame.wrap(context.getClonedGame()));
    return move.getPosition();
  }
}
