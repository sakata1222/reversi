package jp.gr.java_conf.saka.reversi.game.player.impl.com.mcts;

import jp.gr.java_conf.saka.fw.game.com.mcts.MctsExecutor;
import jp.gr.java_conf.saka.fw.game.com.mcts.select.IMctsExecutor;
import jp.gr.java_conf.saka.reversi.game.base.IReadOnlyReversiContext;
import jp.gr.java_conf.saka.reversi.game.base.ReversiColor;
import jp.gr.java_conf.saka.reversi.game.base.ReversiPosition;
import jp.gr.java_conf.saka.reversi.game.player.IReversiPlayer;


public class ReversiMctsPlayer implements IReversiPlayer {

  private ReversiColor playerColor;
  private int maxTotalTries;

  public static ReversiMctsPlayer mctsPlayer(int maxTotalTries) {
    return new ReversiMctsPlayer(maxTotalTries);
  }

  ReversiMctsPlayer(int maxTotalTries) {
    this.maxTotalTries = maxTotalTries;
  }

  @Override
  public String type() {
    return "MCTS Player(" + maxTotalTries + ")";
  }

  @Override
  public IReversiPlayer init(ReversiColor playerColor) {
    this.playerColor = playerColor;
    return this;
  }

  @Override
  public ReversiPosition think(IReadOnlyReversiContext context) {
    IMctsExecutor<MctsReversiGame, MctsReversiMove> executor =
        MctsExecutor.newDefaultInstance(
            MctsReversiColorDictionary.resolve(playerColor),
            maxTotalTries,
            new MctsReversiRandomPlayOutExecutor(playerColor));
    MctsReversiMove move = executor.execute(MctsReversiGame.wrap(context.getClonedGame()));
    return move.getPosition();
  }
}
