package jp.gr.java_conf.saka.reversi.game;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class ReversiBoardTest {

  @Test
  public void test_isOppositeColor() throws Exception {
    ReversiBoard board = new ReversiBoard(8);
    board.put(0, 0, ReversiColor.BLACK);
    assertThat(board.isOppositeColor(0, 0, ReversiColor.WHITE), is(true));
    assertThat(board.isOppositeColor(0, 0, ReversiColor.BLACK), is(false));
    assertThat(board.isOppositeColor(0, 1, ReversiColor.WHITE), is(false));
    assertThat(board.isOppositeColor(0, 1, ReversiColor.BLACK), is(false));
  }

  @Test
  public void test_isSameColor() throws Exception {
    ReversiBoard board = new ReversiBoard(8);
    board.put(0, 0, ReversiColor.BLACK);
    assertThat(board.isSameColor(0, 0, ReversiColor.WHITE), is(false));
    assertThat(board.isSameColor(0, 0, ReversiColor.BLACK), is(true));
    assertThat(board.isSameColor(0, 1, ReversiColor.WHITE), is(false));
    assertThat(board.isSameColor(0, 1, ReversiColor.BLACK), is(false));
  }


}
