package jp.gr.java_conf.saka.reversi.view;

import java.util.function.Supplier;
import jp.gr.java_conf.saka.reversi.game.ReversiBoard;
import jp.gr.java_conf.saka.reversi.game.ReversiColor;
import jp.gr.java_conf.saka.reversi.game.ReversiPosition;
import jp.gr.java_conf.saka.reversi.game.ReversiResult;
import jp.gr.java_conf.saka.reversi.player.IReversiPositionInput;

public interface IReversiViewer {

  void init();

  Supplier<IReversiPositionInput> newInputSupplier();

  void view(ReversiBoard board);

  void onTurn(ReversiColor color);

  void put(ReversiBoard board, ReversiPosition pos, ReversiColor color);

  void gameEnd(ReversiResult result);

  void displayMessage(String message);

  void destroy();
}
