package jp.gr.java_conf.saka.reversi.game.view.cli;

import jp.gr.java_conf.saka.reversi.game.base.ReversiPosition;
import jp.gr.java_conf.saka.reversi.game.player.IReversiPositionInput;

public class ReversiCliInput implements IReversiPositionInput {

  ReversiCliInput() {
  }

  @Override
  public ReversiPosition waitAndGet(int maxWaitTimeInSec) {
    // FIXME
    throw new UnsupportedOperationException("Not implemented yet");
  }
}
