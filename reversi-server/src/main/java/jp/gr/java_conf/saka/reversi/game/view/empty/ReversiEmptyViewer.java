package jp.gr.java_conf.saka.reversi.game.view.empty;

import java.util.function.Supplier;
import jp.gr.java_conf.saka.reversi.game.base.ReversiBoard;
import jp.gr.java_conf.saka.reversi.game.base.ReversiColor;
import jp.gr.java_conf.saka.reversi.game.base.ReversiPosition;
import jp.gr.java_conf.saka.reversi.game.base.ReversiResult;
import jp.gr.java_conf.saka.reversi.game.player.IReversiPositionInput;
import jp.gr.java_conf.saka.reversi.game.view.IReversiViewer;

public class ReversiEmptyViewer implements IReversiViewer {

  @Override
  public void init() {
  }

  @Override
  public Supplier<IReversiPositionInput> newInputSupplier() {
    throw new UnsupportedOperationException("input not supported in EmptyViewer");
  }

  @Override
  public void view(ReversiBoard board) {

  }

  @Override
  public void onTurn(ReversiColor color) {

  }

  @Override
  public void put(ReversiBoard board, ReversiPosition pos, ReversiColor color) {

  }

  @Override
  public void gameEnd(ReversiResult result) {

  }

  @Override
  public void displayMessage(String message) {

  }

  @Override
  public void destroy() {

  }
}
