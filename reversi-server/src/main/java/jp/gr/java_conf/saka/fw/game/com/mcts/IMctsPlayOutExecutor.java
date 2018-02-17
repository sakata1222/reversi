package jp.gr.java_conf.saka.fw.game.com.mcts;

import jp.gr.java_conf.saka.fw.game.base.GamePlayerColor;
import jp.gr.java_conf.saka.fw.game.base.IGame;
import jp.gr.java_conf.saka.fw.game.base.IGameMove;

public interface IMctsPlayOutExecutor<GAME extends IGame<MOVE>, MOVE extends IGameMove> {

  PlayOutResult playOut(GAME game, GamePlayerColor nextPlayer);

  public static enum PlayOutResult {
    WON(true), LOSE(false), DRAW(false);

    private boolean isWon;

    private PlayOutResult(boolean isWon) {
      this.isWon = isWon;
    }

    boolean isWon() {
      return isWon;
    }
  }
}
