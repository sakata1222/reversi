package jp.gr.java_conf.saka.reversi.view.javafx;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import jp.gr.java_conf.saka.reversi.game.ReversiColor;
import jp.gr.java_conf.saka.reversi.game.ReversiPosition;
import jp.gr.java_conf.saka.reversi.player.IReversiPositionInput;

public class ReversiGameApplication extends Application {

  private VBox root;
  private ReversiGameAreaController gameAppController;
  private AtomicBoolean isStarted = new AtomicBoolean(false);
  private static final String JAVA_FX_THREAD_NAME_KEY = "JavaFX";
  private static final Map<String, ReversiGameApplication> INSTANCE_MAP;

  static {
    INSTANCE_MAP = new ConcurrentHashMap<>();
  }

  private static final String REVERSI_GAME_AREA = "reversi_game_area.fxml";

  static ReversiGameApplication getJavaFxLaunchedInstance() {
    return INSTANCE_MAP.entrySet().stream()
        .filter(entry -> entry.getKey().contains(JAVA_FX_THREAD_NAME_KEY)).map(Entry::getValue)
        .findFirst()
        .orElse(null);
  }

  static boolean isJavaFxLaunched() {
    ReversiGameApplication app = getJavaFxLaunchedInstance();
    return Objects.nonNull(app) && app.isStarted();
  }

  /**
   * this method stop until java fx application is stopped.
   */
  static void launchApplication() {
    Application.launch();
  }

  /**
   * This constructor is called from Application.launch by reflection.
   */
  public ReversiGameApplication() {
    INSTANCE_MAP.put(Thread.currentThread().getName(), this);
  }

  @Override
  public void start(Stage stage) throws Exception {
    FXMLLoader loader = new FXMLLoader(getClass().getResource(REVERSI_GAME_AREA));
    root = loader.load();
    gameAppController = loader.getController();

    Platform.runLater(() -> {
      stage.sizeToScene();
    });
    Scene scene = new Scene(root);
    stage.setScene(scene);
    isStarted.set(true);
    stage.show();
  }

  IReversiPositionInput newInput() {
    return gameAppController.newInput();
  }

  boolean isStarted() {
    return isStarted.get();
  }

  void putPiece(ReversiPosition position, ReversiColor color) {
    gameAppController.putPiece(position, color);
  }
}
