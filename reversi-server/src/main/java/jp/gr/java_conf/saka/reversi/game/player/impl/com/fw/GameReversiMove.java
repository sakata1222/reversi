package jp.gr.java_conf.saka.reversi.game.player.impl.com.fw;

import jp.gr.java_conf.saka.fw.game.base.IGameMove;
import jp.gr.java_conf.saka.reversi.game.base.ReversiPosition;

public class GameReversiMove implements IGameMove {

  private ReversiPosition position;

  static GameReversiMove wrap(ReversiPosition position) {
    return new GameReversiMove(position);
  }

  private GameReversiMove(ReversiPosition position) {
    this.position = position;
  }

  public ReversiPosition getPosition() {
    return position;
  }
}
