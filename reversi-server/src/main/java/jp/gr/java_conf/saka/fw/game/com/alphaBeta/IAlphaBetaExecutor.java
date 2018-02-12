package jp.gr.java_conf.saka.fw.game.com.alphaBeta;

import jp.gr.java_conf.saka.fw.game.base.IGame;
import jp.gr.java_conf.saka.fw.game.base.IGameMove;

public interface IAlphaBetaExecutor<GAME extends IGame<MOVE>, MOVE extends IGameMove> {

  MOVE execute(GAME game);
}
