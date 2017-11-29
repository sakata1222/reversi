package jp.gr.java_conf.saka.reversi;

import jp.gr.java_conf.saka.reversi.player.impl.com.random.ReversiRandomPlayer;
import jp.gr.java_conf.saka.reversi.view.javafx.ReversiJavaFxViewer;

public class ReversiGameMain {

  public static void main(String[] args) {
    try {
      ReversiGameMaster gameMaster = ReversiGameMaster
          .newGame(new ReversiJavaFxViewer(), new ReversiRandomPlayer(), new ReversiRandomPlayer());
      gameMaster.start();
    } catch (Throwable t) {
      t.printStackTrace();
    }
  }
}
