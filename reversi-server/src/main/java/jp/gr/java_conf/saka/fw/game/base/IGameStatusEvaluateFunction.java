package jp.gr.java_conf.saka.fw.game.base;

public interface IGameStatusEvaluateFunction<GAME extends IGame<? extends IGameMove>> {

  long evaluate(GamePlayerColor color, GAME game);
}
