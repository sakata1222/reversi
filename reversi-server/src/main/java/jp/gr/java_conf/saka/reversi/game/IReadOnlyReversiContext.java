package jp.gr.java_conf.saka.reversi.game;

public interface IReadOnlyReversiContext {

  ReversiGame getClonedGame();

  int getMaxThinkingTimeInSec();
}
