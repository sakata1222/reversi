package jp.gr.java_conf.saka.reversi.game.player.impl.com.mcts;

import jp.gr.java_conf.saka.fw.game.com.mcts.MctsExecutorReuseImpl;
import jp.gr.java_conf.saka.fw.game.com.mcts.select.IMctsExecutor;
import jp.gr.java_conf.saka.reversi.game.base.IReadOnlyReversiContext;
import jp.gr.java_conf.saka.reversi.game.base.ReversiColor;
import jp.gr.java_conf.saka.reversi.game.base.ReversiPosition;
import jp.gr.java_conf.saka.reversi.game.player.IReversiPlayer;
import jp.gr.java_conf.saka.reversi.game.player.impl.com.fw.GameReversiMove;
import jp.gr.java_conf.saka.reversi.game.player.impl.com.fw.ReversiColorDictionary;
import jp.gr.java_conf.saka.reversi.game.player.impl.com.fw.ReversiGameWrapper;


public class ReversiMctsReusePlayer implements IReversiPlayer {

  private ReversiColor playerColor;
  private int maxTotalTries;
  private IMctsExecutor<ReversiGameWrapper, GameReversiMove> executor;

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
        ReversiColorDictionary.resolve(playerColor),
        maxTotalTries,
        new MctsReversiRandomPlayOutExecutor(playerColor));
    return this;
  }

  @Override
  public ReversiPosition think(IReadOnlyReversiContext context) {
    GameReversiMove move = executor.execute(ReversiGameWrapper.wrap(context.getClonedGame()));
    return move.getPosition();
  }
}
