package jp.gr.java_conf.saka.reversi.game.player.impl.com.random;

import java.util.Collections;
import java.util.List;
import jp.gr.java_conf.saka.reversi.game.base.IReadOnlyReversiContext;
import jp.gr.java_conf.saka.reversi.game.base.ReversiColor;
import jp.gr.java_conf.saka.reversi.game.base.ReversiPosition;
import jp.gr.java_conf.saka.reversi.game.player.IReversiPlayer;

public class ReversiRandomPlayer implements IReversiPlayer {

  private ReversiColor playerColor;

  public static IReversiPlayer randomPlayer() {
    return new ReversiRandomPlayer();
  }

  ReversiRandomPlayer() {
  }

  @Override
  public String type() {
    return "Random";
  }

  @Override
  public IReversiPlayer init(ReversiColor playerColor) {
    this.playerColor = playerColor;
    return this;
  }

  @Override
  public ReversiPosition think(IReadOnlyReversiContext context) {
    List<ReversiPosition> candidates = context.getClonedGame().puttablePositions(playerColor);
    Collections.shuffle(candidates);
    return candidates.get(0);
  }
}
