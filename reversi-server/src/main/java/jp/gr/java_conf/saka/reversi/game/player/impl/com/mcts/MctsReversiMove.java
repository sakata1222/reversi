package jp.gr.java_conf.saka.reversi.game.player.impl.com.mcts;

import jp.gr.java_conf.saka.fw.game.com.mcts.IMctsMove;
import jp.gr.java_conf.saka.reversi.game.base.ReversiPosition;

public class MctsReversiMove implements IMctsMove {

  private ReversiPosition position;

  static MctsReversiMove wrap(ReversiPosition position) {
    return new MctsReversiMove(position);
  }

  private MctsReversiMove(ReversiPosition position) {
    this.position = position;
  }

  ReversiPosition getPosition() {
    return position;
  }
}
