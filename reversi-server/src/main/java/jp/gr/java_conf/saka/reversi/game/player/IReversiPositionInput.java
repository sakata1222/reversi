package jp.gr.java_conf.saka.reversi.game.player;

import jp.gr.java_conf.saka.reversi.game.base.ReversiPosition;

public interface IReversiPositionInput {

  ReversiPosition waitAndGet(int maxWaitTimeInSec);
}
