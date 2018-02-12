package jp.gr.java_conf.saka.fw.game.com.mcts.select;

import java.util.List;
import jp.gr.java_conf.saka.fw.game.base.IGame;
import jp.gr.java_conf.saka.fw.game.base.IGameMove;
import jp.gr.java_conf.saka.fw.game.com.mcts.MctsNode;

public interface IMctsNodeSelector {

  <GAME extends IGame<MOVE>, MOVE extends IGameMove> MctsNode<GAME, MOVE> select(
      List<MctsNode<GAME, MOVE>> children);
}
