package jp.gr.java_conf.saka.fw.game.com.mcts.select;

import jp.gr.java_conf.saka.fw.game.base.IGame;
import jp.gr.java_conf.saka.fw.game.base.IGameMove;

public interface IMctsExecutor<GAME extends IGame<MOVE>, MOVE extends IGameMove> {

  MOVE execute(GAME game);
}
