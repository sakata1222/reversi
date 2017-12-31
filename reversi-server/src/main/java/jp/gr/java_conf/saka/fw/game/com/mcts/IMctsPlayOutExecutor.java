package jp.gr.java_conf.saka.fw.game.com.mcts;

import jp.gr.java_conf.saka.fw.game.base.GamePlayerColor;

public interface IMctsPlayOutExecutor<GAME extends IMctsGame<MOVE>, MOVE extends IMctsMove> {

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
