package jp.gr.java_conf.saka.reversi.game.base;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class ReversiRuleTest {

  @Test
  public void isPutaable_upper_left_to_lower_right() throws Exception {
    ReversiRule rule = new ReversiRule();
    ReversiBoard board = new ReversiBoard(8);
    board.put(2, 2, ReversiColor.WHITE);
    board.put(3, 3, ReversiColor.BLACK);

    assertThat(rule.isPutaable(board, ReversiPosition.xy(0, 0), ReversiColor.BLACK),
        is(false));
    assertThat(rule.isPutaable(board, ReversiPosition.xy(0, 0), ReversiColor.WHITE),
        is(false));

    assertThat(rule.isPutaable(board, ReversiPosition.xy(1, 1), ReversiColor.BLACK),
        is(true));
    assertThat(rule.isPutaable(board, ReversiPosition.xy(1, 1), ReversiColor.WHITE),
        is(false));

    assertThat(rule.isPutaable(board, ReversiPosition.xy(2, 2), ReversiColor.BLACK),
        is(false));
    assertThat(rule.isPutaable(board, ReversiPosition.xy(2, 2), ReversiColor.WHITE),
        is(false));

    assertThat(rule.isPutaable(board, ReversiPosition.xy(3, 3), ReversiColor.BLACK),
        is(false));
    assertThat(rule.isPutaable(board, ReversiPosition.xy(3, 3), ReversiColor.WHITE),
        is(false));

    assertThat(rule.isPutaable(board, ReversiPosition.xy(4, 4), ReversiColor.BLACK),
        is(false));
    assertThat(rule.isPutaable(board, ReversiPosition.xy(4, 4), ReversiColor.WHITE),
        is(true));

  }
}
