package jp.gr.java_conf.saka.reversi.view.javafx;

import java.util.function.Supplier;
import java.util.stream.IntStream;
import jp.gr.java_conf.saka.reversi.game.ReversiBoard;
import jp.gr.java_conf.saka.reversi.game.ReversiColor;
import jp.gr.java_conf.saka.reversi.game.ReversiPosition;
import jp.gr.java_conf.saka.reversi.game.ReversiResult;
import jp.gr.java_conf.saka.reversi.player.IReversiPositionInput;
import jp.gr.java_conf.saka.reversi.view.IReversiViewer;

public class ReversiJavaFxViewer implements IReversiViewer {

  private ReversiGameApplication application;

  @Override
  public void init() {
    Thread thread = new Thread(() -> {
      try {
        ReversiGameApplication.launchApplication();
      } catch (Throwable t) {
        t.printStackTrace();
      }
    });
    thread.setDaemon(true);
    thread.start();
    while (!ReversiGameApplication.isJavaFxLaunched()) {
      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        e.printStackTrace();
        throw new RuntimeException(e);
      }
    }
    application = ReversiGameApplication.getJavaFxLaunchedInstance();
  }

  @Override
  public Supplier<IReversiPositionInput> newInputSupplier() {
    return () -> application.newInput();
  }

  @Override
  public void view(ReversiBoard board) {
    int size = board.getSize();
    IntStream.range(0, size).forEach(y -> {
      IntStream.range(0, size).forEach(x -> {
        ReversiPosition pos = ReversiPosition.xy(x, y);
        board.getColorAsOptional(pos).ifPresent(color -> application.putPiece(pos, color));
      });
    });
  }

  @Override
  public void onTurn(ReversiColor color) {
    application.displayMessage(String.valueOf(color));
  }

  @Override
  public void put(ReversiBoard board, ReversiPosition pos, ReversiColor color) {
    application.putPiece(pos, color);
  }

  @Override
  public void gameEnd(ReversiResult result) {
    application.displayMessage(String.valueOf(result.getResultType()));
  }

  @Override
  public void displayMessage(String message) {
    application.displayMessage(message);
  }

  @Override
  public void destroy() {
    // TODO wait close button is clicked.
    try {
      Thread.sleep(5000);
    } catch (InterruptedException e) {
      e.printStackTrace();
      throw new RuntimeException(e);
    }

    //Platform.exit();
  }
}
