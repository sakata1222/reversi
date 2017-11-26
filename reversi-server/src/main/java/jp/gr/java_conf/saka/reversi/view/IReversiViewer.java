package jp.gr.java_conf.saka.reversi.view;

import jp.gr.java_conf.saka.reversi.game.ReversiBoard;
import jp.gr.java_conf.saka.reversi.game.ReversiColor;
import jp.gr.java_conf.saka.reversi.game.ReversiResult;

public interface IReversiViewer {

  void init();

  void view(ReversiBoard board);

  void onTurn(ReversiColor color);

  void gameEnd(ReversiResult result);

  void displayMessage(String message);
}
