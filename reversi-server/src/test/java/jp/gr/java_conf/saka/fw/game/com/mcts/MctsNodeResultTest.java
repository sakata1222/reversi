package jp.gr.java_conf.saka.fw.game.com.mcts;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class MctsNodeResultTest {

  @Test
  public void win() {
    MctsNodeResult result = new MctsNodeResult();
    assertThat(result.getTotal(), is(0));
    assertThat(result.getWins(), is(0));
    result.win();
    assertThat(result.getTotal(), is(1));
    assertThat(result.getWins(), is(1));
    assertThat(result.rate(), is(1.0d));
  }

  @Test
  public void lose() {
    MctsNodeResult result = new MctsNodeResult();
    assertThat(result.getTotal(), is(0));
    assertThat(result.getWins(), is(0));
    result.lose();
    assertThat(result.getTotal(), is(1));
    assertThat(result.getWins(), is(0));
    assertThat(result.rate(), is(0.0d));
  }

  @Test
  public void addResult() {
    MctsNodeResult result = new MctsNodeResult();
    assertThat(result.getTotal(), is(0));
    assertThat(result.getWins(), is(0));
    result.addResult(true);
    assertThat(result.getTotal(), is(1));
    assertThat(result.getWins(), is(1));
    assertThat(result.rate(), is(1.0d));
    result.addResult(false);
    assertThat(result.getTotal(), is(2));
    assertThat(result.getWins(), is(1));
    assertThat(result.rate(), is(0.5d));
  }
}