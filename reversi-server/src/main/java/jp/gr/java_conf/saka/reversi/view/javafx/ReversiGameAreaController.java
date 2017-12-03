package jp.gr.java_conf.saka.reversi.view.javafx;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import jp.gr.java_conf.saka.reversi.game.ReversiPosition;
import jp.gr.java_conf.saka.reversi.player.IReversiPositionInput;

public class ReversiGameAreaController implements Initializable {

  private Map<Pane, ReversiPosition> listnerToPosition;

  private BlockingQueue<ReversiPosition> inputQueue;

  @FXML
  private GridPane reversiBoardGrid;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    listnerToPosition = new ConcurrentHashMap<>();
    inputQueue = new ArrayBlockingQueue<>(10, true);

    IntStream.range(0, reversiBoardGrid.getColumnConstraints().size()).forEach(x -> {
      IntStream.range(0, reversiBoardGrid.getRowConstraints().size()).forEach(y -> {
        Pane pane = new Pane();
        pane.getStyleClass().add("board-event-listen-node");
        listnerToPosition.put(pane, ReversiPosition.xy(x, y));
        pane.setOnMouseClicked(event -> {
          synchronized (inputQueue) {
            ReversiPosition pos = listnerToPosition.get(pane);
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

  IReversiPositionInput newInput() {
    return new ReversiFxInput();
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

