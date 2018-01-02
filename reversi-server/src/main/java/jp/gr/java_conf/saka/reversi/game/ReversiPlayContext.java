package jp.gr.java_conf.saka.reversi.game;

public class ReversiPlayContext {

  private ReversiGame game;
  private int maxThinkingTimeInSec;

  public static ReversiPlayContext fixedThinkingTime(ReversiGame game, int maxThinkingTimeInSec) {
    return new ReversiPlayContext(game, maxThinkingTimeInSec);
  }

  ReversiPlayContext(ReversiGame game, int maxThinkingTimeInSec) {
    this.game = game;
    this.maxThinkingTimeInSec = maxThinkingTimeInSec;
  }

  public ReversiGame getGame() {
    return game;
  }

  public int getMaxThinkingTimeInSec() {
    return maxThinkingTimeInSec;
  }

  public void setGame(ReversiGame game) {
    this.game = game;
  }

  public void setMaxThinkingTimeInSec(int maxThinkingTimeInSec) {
    this.maxThinkingTimeInSec = maxThinkingTimeInSec;
  }

  public IReadOnlyReversiContext readOnly() {
    return new ReadOnlyReversiPlayContext();
  }

  class ReadOnlyReversiPlayContext implements IReadOnlyReversiContext {

    @Override
    public ReversiGame getClonedGame() {
      return getGame().clone();
    }

    @Override
    public int getMaxThinkingTimeInSec() {
      return maxThinkingTimeInSec;
    }
  }
}
