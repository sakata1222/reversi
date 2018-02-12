package jp.gr.java_conf.saka.reversi;

import jp.gr.java_conf.saka.reversi.game.ReversiGameMaster;
import jp.gr.java_conf.saka.reversi.game.player.ReversiPlayers;
import jp.gr.java_conf.saka.reversi.game.view.IReversiViewer;
import jp.gr.java_conf.saka.reversi.game.view.javafx.ReversiJavaFxViewer;

public class ReversiGameMain {

  public static void main(String[] args) {
    try {
      IReversiViewer viewer = new ReversiJavaFxViewer();
      ReversiGameMaster gameMaster = ReversiGameMaster.newGame(
          viewer,
          ReversiPlayers.human(viewer.newInputSupplier()),
          ReversiPlayers.simpleAlphaBeta(1));
      gameMaster.start();
    } catch (Throwable t) {
      t.printStackTrace();
    }
  }
}
