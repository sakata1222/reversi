package jp.gr.java_conf.saka.reversi.player.impl.com.mcts;

import jp.gr.java_conf.saka.fw.game.com.mcts.MctsExecutor;
import jp.gr.java_conf.saka.reversi.game.IReadOnlyReversiContext;
import jp.gr.java_conf.saka.reversi.game.ReversiColor;
import jp.gr.java_conf.saka.reversi.game.ReversiPosition;
import jp.gr.java_conf.saka.reversi.player.IReversiPlayer;

public class ReversiMctsPlayer implements IReversiPlayer {

  private ReversiColor playerColor;

  public static ReversiMctsPlayer mctsPlayer() {
    return new ReversiMctsPlayer();
  }

  @Override
  public String type() {
    return "MCTS Player";
  }

  @Override
  public IReversiPlayer init(ReversiColor playerColor) {
    this.playerColor = playerColor;
    return this;
  }

  @Override
  public ReversiPosition think(IReadOnlyReversiContext context) {
    MctsExecutor<MctsReversiGame, MctsReversiMove> executor =
        MctsExecutor
            .newDefaultInstance(MctsReversiColorDictionary.resolve(playerColor), 500,
                new MctsReversiRandomPlayOutExecutor(playerColor));
    MctsReversiMove move = executor.execute(MctsReversiGame.wrap(context.getClonedGame()));
    return move.getPosition();
  }
}
