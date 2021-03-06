package jp.gr.java_conf.saka.reversi.game.view.javafx;

import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.StrokeType;
import jp.gr.java_conf.saka.reversi.game.base.ReversiColor;
import jp.gr.java_conf.saka.reversi.game.base.ReversiPosition;
import jp.gr.java_conf.saka.reversi.game.player.IReversiPositionInput;

public class ReversiGameAreaController implements Initializable {

  private static final Map<ReversiColor, String> PIECE_CSS_CLASS_MAP;

  static {
    Map<ReversiColor, String> map = new HashMap<>();
    map.put(ReversiColor.BLACK, "black-piece");
    map.put(ReversiColor.WHITE, "white-piece");
    PIECE_CSS_CLASS_MAP = Collections.unmodifiableMap(map);
  }

  private Map<Pane, ReversiPosition> listenerToPosition;

  private BlockingQueue<ReversiPosition> inputQueue;
  private Map<ReversiPosition, Circle> pieces = new HashMap<>();
  @FXML
  private GridPane reversiBoardGrid;
  @FXML
  private TextArea reversiInfoTextArea;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    listenerToPosition = new ConcurrentHashMap<>();
    inputQueue = new ArrayBlockingQueue<>(10, true);

    IntStream.range(0, reversiBoardGrid.getColumnConstraints().size()).forEach(x -> {
      IntStream.range(0, reversiBoardGrid.getRowConstraints().size()).forEach(y -> {
        Pane pane = new Pane();
        pane.getStyleClass().add("board-event-listen-node");
        listenerToPosition.put(pane, ReversiPosition.xy(x, y));
        pane.setOnMouseClicked(event -> {
          synchronized (inputQueue) {
            ReversiPosition pos = listenerToPosition.get(pane);
            if (!inputQueue.offer(pos)) {
              inputQueue.clear();
              inputQueue.add(pos);
            }
          }
        });
        reversiBoardGrid.add(pane, x, y);
      });
    });
  }

  void putPiece(ReversiPosition position, ReversiColor color) {
    if (pieces.containsKey(position)) {
      JavaFxPlatformAccessUtils.runSync(() -> changeColor(pieces.get(position), color));
    } else {
      Circle piece = newPiece(color);
      pieces.put(position, piece);
      JavaFxPlatformAccessUtils
          .runSync(() -> reversiBoardGrid.add(piece, position.getX(), position.getY()));
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

  IReversiPositionInput newInput() {
    return new ReversiFxInput();
  }

  void setMessage(String text) {
    JavaFxPlatformAccessUtils.runSync(() -> reversiInfoTextArea.setText(text));
  }

  class ReversiFxInput implements IReversiPositionInput {

    @Override
    public ReversiPosition waitAndGet(int maxWaitTimeInSec) {
      inputQueue.clear();
      try {
        return inputQueue.poll(maxWaitTimeInSec, TimeUnit.SECONDS);
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      }
    }
  }
}

