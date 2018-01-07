package jp.gr.java_conf.saka.reversi.game.base;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Optional;
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

  @Test
  public void test_clone() {
    ReversiBoard board = new ReversiBoard(8);
    board.put(0, 0, ReversiColor.BLACK);
    board.put(1, 4, ReversiColor.WHITE);
    board.put(5, 2, ReversiColor.BLACK);
    board.put(6, 6, ReversiColor.WHITE);
    ReversiBoard cloned = board.clone();
    assertThat(cloned.getColorAsOptional(0, 0).get(), is(ReversiColor.BLACK));
    assertThat(cloned.getColorAsOptional(1, 4).get(), is(ReversiColor.WHITE));
    assertThat(cloned.getColorAsOptional(5, 2).get(), is(ReversiColor.BLACK));
    assertThat(cloned.getColorAsOptional(6, 6).get(), is(ReversiColor.WHITE));

    assertThat(cloned.getColorAsOptional(0, 1), is(Optional.empty()));
    assertThat(cloned.getColorAsOptional(1, 0), is(Optional.empty()));
    assertThat(cloned.getColorAsOptional(4, 1), is(Optional.empty()));
    assertThat(cloned.getColorAsOptional(2, 5), is(Optional.empty()));
    assertThat(cloned.getColorAsOptional(7, 7), is(Optional.empty()));


  }
}
