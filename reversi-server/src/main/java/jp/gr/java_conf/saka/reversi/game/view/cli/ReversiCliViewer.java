package jp.gr.java_conf.saka.reversi.game.view.cli;

import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import jp.gr.java_conf.saka.reversi.game.base.ReversiBoard;
import jp.gr.java_conf.saka.reversi.game.base.ReversiColor;
import jp.gr.java_conf.saka.reversi.game.base.ReversiPosition;
import jp.gr.java_conf.saka.reversi.game.base.ReversiResult;
import jp.gr.java_conf.saka.reversi.game.player.IReversiPositionInput;
import jp.gr.java_conf.saka.reversi.game.view.IReversiViewer;

public class ReversiCliViewer implements IReversiViewer {

  @Override
  public void init() {

  }

  @Override
  public Supplier<IReversiPositionInput> newInputSupplier() {
    return () -> new ReversiCliInput();
  }

  @Override
  public void view(ReversiBoard board) {
    System.out.println(" |" +//
        IntStream.range(0, board.getSize()).mapToObj(String::valueOf)
            .collect(Collectors.joining("|")));
    IntStream.range(0, board.getSize()).forEach(y -> {
      System.out.print(y + "|");
      System.out.println(
          IntStream.range(0, board.getSize()).mapToObj(
              x -> board.getColorAsOptional(x, y).map(color -> color.getShortString()).orElse(" "))
              .collect(Collectors.joining("|"))
      );
    });
  }

  @Override
  public void onTurn(ReversiColor color) {
    System.out.println();
    System.out.println("Turn:" + color);
  }

  @Override
  public void put(ReversiBoard board, ReversiPosition pos, ReversiColor color) {

  }

  @Override
  public void gameEnd(ReversiResult result) {
    System.out.println(result.getResultType());
    System.out.println(result.getPiecesCount());
  }

  @Override
  public void displayMessage(String message) {
    System.out.println(message);
  }

  @Override
  public void destroy() {
  }
}
