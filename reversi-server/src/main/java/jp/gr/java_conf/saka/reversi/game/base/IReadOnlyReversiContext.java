package jp.gr.java_conf.saka.reversi.game.base;

public interface IReadOnlyReversiContext {

  ReversiGame getClonedGame();

  int getMaxThinkingTimeInSec();
}
