package jp.gr.java_conf.saka.reversi;

import jp.gr.java_conf.saka.reversi.player.ReversiPlayers;
import jp.gr.java_conf.saka.reversi.view.IReversiViewer;
import jp.gr.java_conf.saka.reversi.view.javafx.ReversiJavaFxViewer;

public class ReversiGameMain {

  public static void main(String[] args) {
    try {
      IReversiViewer viewer = new ReversiJavaFxViewer();
      ReversiGameMaster gameMaster = ReversiGameMaster
          .newGame(viewer, ReversiPlayers.human(viewer.newInputSupplier()),
              ReversiPlayers.random());
      gameMaster.start();
    } catch (Throwable t) {
      t.printStackTrace();
    }
  }
}
