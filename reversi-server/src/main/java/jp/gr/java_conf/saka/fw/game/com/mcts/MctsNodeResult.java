package jp.gr.java_conf.saka.fw.game.com.mcts;

import java.util.concurrent.atomic.AtomicInteger;

public class MctsNodeResult {

  private AtomicInteger total;
  private AtomicInteger wins;

  MctsNodeResult() {
    total = new AtomicInteger(0);
    wins = new AtomicInteger(0);
  }

  MctsNodeResult win() {
    return addResult(true);
  }

  MctsNodeResult lose() {
    return addResult(false);
  }

  MctsNodeResult addResult(boolean isWon) {
    if (isWon) {
      wins.incrementAndGet();
    }
    total.incrementAndGet();
    return this;
  }

  public int getTotal() {
    return total.intValue();
  }

  public int getWins() {
    return wins.intValue();
  }

  public double rate() {
    return (double) wins.intValue() / (double) total.intValue();
  }

}
