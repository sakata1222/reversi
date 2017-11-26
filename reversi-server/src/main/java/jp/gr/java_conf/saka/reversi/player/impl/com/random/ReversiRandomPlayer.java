package jp.gr.java_conf.saka.reversi.player.impl.com.random;

import java.util.Collections;
import java.util.List;
import jp.gr.java_conf.saka.reversi.game.IReadOnlyReversiContext;
import jp.gr.java_conf.saka.reversi.game.ReversiColor;
import jp.gr.java_conf.saka.reversi.game.ReversiPosition;
import jp.gr.java_conf.saka.reversi.player.IReversiPlayer;

public class ReversiRandomPlayer implements IReversiPlayer {

  private ReversiColor playerColor;

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
