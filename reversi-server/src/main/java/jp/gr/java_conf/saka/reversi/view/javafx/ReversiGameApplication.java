package jp.gr.java_conf.saka.reversi.view.javafx;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.StrokeType;
import javafx.stage.Stage;
import jp.gr.java_conf.saka.reversi.game.ReversiColor;
import jp.gr.java_conf.saka.reversi.game.ReversiPosition;
import jp.gr.java_conf.saka.reversi.player.IReversiPositionInput;

public class ReversiGameApplication extends Application {

  private VBox root;
  private ReversiGameAreaController gameAppController;
  private GridPane reversiBoard;
  private AtomicBoolean isStarted = new AtomicBoolean(false);
  private Map<ReversiPosition, Circle> pieces = new HashMap<>();
  private static final String JAVA_FX_THREAD_NAME_KEY = "JavaFX";
  private static final Map<ReversiColor, String> PIECE_CSS_CLASS_MAP;
  private static final Map<String, ReversiGameApplication> INSTANCE_MAP;

  static {
    Map<ReversiColor, String> map = new HashMap<>();
    map.put(ReversiColor.BLACK, "black-piece");
    map.put(ReversiColor.WHITE, "white-piece");
    PIECE_CSS_CLASS_MAP = Collections.unmodifiableMap(map);

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
      reversiBoard = Objects.requireNonNull(GridPane.class.cast(root.lookup("#reversiBoardGrid")));
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
    if (pieces.containsKey(position)) {
      changeColor(pieces.get(position), color);
    } else {
      Circle piece = newPiece(color);
      pieces.put(position, piece);
      JavaFxPlatformAccessUtils
          .runSync(() -> reversiBoard.add(piece, position.getX(), position.getY()));
      piece.setVisible(true);
    }
  }

  private Circle changeColor(Circle piece, ReversiColor color) {
    piece.getStyleClass().removeAll(PIECE_CSS_CLASS_MAP.values());
    piece.getStyleClass().add(PIECE_CSS_CLASS_MAP.get(color));
    return piece;
  }

  private Circle newPiece(ReversiColor color) {
    Circle piece = new Circle();
    piece.setRadius(30.0);
    piece.setStroke(Color.BLACK);
    piece.setStrokeType(StrokeType.INSIDE);
    changeColor(piece, color);
    return piece;
  }

}
