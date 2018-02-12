package jp.gr.java_conf.saka.fw.game.base;

import java.util.List;

public interface IGame<MOVE extends IGameMove> extends Cloneable {

  List<MOVE> puttableMoves(GamePlayerColor color);

  IGame clone();

  void put(MOVE move, GamePlayerColor childColor);

  boolean isSameState(IGame<MOVE> otherGame);
}
