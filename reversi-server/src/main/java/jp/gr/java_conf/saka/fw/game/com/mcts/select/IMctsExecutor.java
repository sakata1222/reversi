package jp.gr.java_conf.saka.fw.game.com.mcts.select;

import jp.gr.java_conf.saka.fw.game.com.mcts.IMctsGame;
import jp.gr.java_conf.saka.fw.game.com.mcts.IMctsMove;

public interface IMctsExecutor<GAME extends IMctsGame<MOVE>, MOVE extends IMctsMove> {

  MOVE execute(GAME game);
}
