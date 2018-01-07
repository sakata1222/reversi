package jp.gr.java_conf.saka.fw.game.com.mcts;

import java.util.List;
import jp.gr.java_conf.saka.fw.game.base.GamePlayerColor;

public interface IMctsGame<MOVE extends IMctsMove> extends Cloneable {

  List<MOVE> puttableMoves(GamePlayerColor color);

  IMctsGame clone();

  void put(MOVE move, GamePlayerColor childColor);

  boolean isSameState(IMctsGame<MOVE> otherGame);
}
