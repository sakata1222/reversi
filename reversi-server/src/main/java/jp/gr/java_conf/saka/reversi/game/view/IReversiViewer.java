package jp.gr.java_conf.saka.reversi.game.view;

import java.util.function.Supplier;
import jp.gr.java_conf.saka.reversi.game.base.ReversiBoard;
import jp.gr.java_conf.saka.reversi.game.base.ReversiColor;
import jp.gr.java_conf.saka.reversi.game.base.ReversiPosition;
import jp.gr.java_conf.saka.reversi.game.base.ReversiResult;
import jp.gr.java_conf.saka.reversi.game.player.IReversiPositionInput;

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
