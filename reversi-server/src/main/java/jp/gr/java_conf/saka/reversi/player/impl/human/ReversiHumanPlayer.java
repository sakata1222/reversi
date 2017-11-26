package jp.gr.java_conf.saka.reversi.player.impl.human;

import jp.gr.java_conf.saka.reversi.game.IReadOnlyReversiContext;
import jp.gr.java_conf.saka.reversi.game.ReversiColor;
import jp.gr.java_conf.saka.reversi.game.ReversiPosition;
import jp.gr.java_conf.saka.reversi.player.IReversiPlayer;

public class ReversiHumanPlayer implements IReversiPlayer {

  @Override
  public String type() {
    return "Human";
  }

  @Override
  public IReversiPlayer init(ReversiColor playerColor) {
    return this;
  }

  @Override
  public ReversiPosition think(IReadOnlyReversiContext context) {
    return null;
  }
}
