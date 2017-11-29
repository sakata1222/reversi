package jp.gr.java_conf.saka.reversi.view.javafx;

import java.util.stream.IntStream;
import jp.gr.java_conf.saka.reversi.game.ReversiBoard;
import jp.gr.java_conf.saka.reversi.game.ReversiColor;
import jp.gr.java_conf.saka.reversi.game.ReversiPosition;
import jp.gr.java_conf.saka.reversi.game.ReversiResult;
import jp.gr.java_conf.saka.reversi.view.IReversiViewer;

public class ReversiJavaFxViewer implements IReversiViewer {

  private ReversiApplication application;

  @Override
  public void init() {
    Thread thread = new Thread(() -> {
      try {
        ReversiApplication.launchApplication();
      } catch (Throwable t) {
        t.printStackTrace();
      }
    });
    thread.setDaemon(true);
    thread.start();
    while (!ReversiApplication.isJavaFxLaunched()) {
      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        e.printStackTrace();
        throw new RuntimeException(e);
      }
    }
    application = ReversiApplication.getJavaFxLaunchedInstance();
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

  }

  @Override
  public void put(ReversiBoard board, ReversiPosition pos, ReversiColor color) {
    application.putPiece(pos, color);
    try {
      Thread.sleep(500L);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void gameEnd(ReversiResult result) {

  }

  @Override
  public void displayMessage(String message) {

  }

  @Override
  public void destroy() {
    //Platform.exit();
  }
}
