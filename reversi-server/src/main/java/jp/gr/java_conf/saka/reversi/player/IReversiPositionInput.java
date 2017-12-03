package jp.gr.java_conf.saka.reversi.player;

import jp.gr.java_conf.saka.reversi.game.ReversiPosition;

public interface IReversiPositionInput {

  ReversiPosition waitAndGet(int maxWaitTimeInSec);
}
