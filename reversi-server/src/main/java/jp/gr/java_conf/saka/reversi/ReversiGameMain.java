package jp.gr.java_conf.saka.reversi;

import jp.gr.java_conf.saka.reversi.player.impl.com.random.ReversiRandomPlayer;

public class ReversiGameMain {

  public static void main(String[] args) {
    ReversiGameMaster gameMaster = ReversiGameMaster
        .newGame(new ReversiRandomPlayer(), new ReversiRandomPlayer());
    gameMaster.start();
  }
}
