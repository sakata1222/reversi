package jp.gr.java_conf.saka.reversi.view.javafx;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.RunnableFuture;
import javafx.application.Platform;

public class JavaFxPlatformAccessUtils {

  public static void runSync(Runnable runnable) {
    if (Platform.isFxApplicationThread()) {
      runnable.run();
      return;
    }
    RunnableFuture future = new FutureTask<Void>(runnable, null);
    Platform.runLater(future);
    try {
      future.get();
    } catch (InterruptedException | ExecutionException e) {
      throw new RuntimeException(e);
    }
  }
}
