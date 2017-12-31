package jp.gr.java_conf.saka.fw.game.com.mcts;

public class MctsNodeResult implements Cloneable {

  int total;
  int wins;

  MctsNodeResult() {
    total = 0;
    wins = 0;
  }

  MctsNodeResult win() {
    return addResult(true);
  }

  MctsNodeResult lose() {
    return addResult(false);
  }

  MctsNodeResult addResult(boolean isWon) {
    if (isWon) {
      wins++;
    }
    total++;
    return this;
  }

  public int getTotal() {
    return total;
  }

  public int getWins() {
    return wins;
  }

  public double rate() {
    return wins / total;
  }

  @Override
  public MctsNodeResult clone() {
    try {
      MctsNodeResult cloned = MctsNodeResult.class.cast(super.clone());
      cloned.total = this.total;
      cloned.wins = this.wins;
      return cloned;
    } catch (CloneNotSupportedException e) {
      throw new RuntimeException(e);
    }
  }
}
