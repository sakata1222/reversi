package jp.gr.java_conf.saka.fw.game.com.mcts.select;

import java.util.List;
import jp.gr.java_conf.saka.fw.game.com.mcts.IMctsGame;
import jp.gr.java_conf.saka.fw.game.com.mcts.IMctsMove;
import jp.gr.java_conf.saka.fw.game.com.mcts.MctsNode;

public interface IMctsNodeSelector {

  <GAME extends IMctsGame<MOVE>, MOVE extends IMctsMove> MctsNode<GAME, MOVE> select(
      List<MctsNode<GAME, MOVE>> children);
}
