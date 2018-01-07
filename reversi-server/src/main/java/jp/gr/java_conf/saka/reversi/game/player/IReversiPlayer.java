package jp.gr.java_conf.saka.reversi.game.player;

import jp.gr.java_conf.saka.reversi.game.base.IReadOnlyReversiContext;
import jp.gr.java_conf.saka.reversi.game.base.ReversiColor;
import jp.gr.java_conf.saka.reversi.game.base.ReversiPosition;

public interface IReversiPlayer {

  String type();

  IReversiPlayer init(ReversiColor playerColor);

  ReversiPosition think(IReadOnlyReversiContext context);
}
