package jp.gr.java_conf.saka.reversi.player;

import jp.gr.java_conf.saka.reversi.game.IReadOnlyReversiContext;
import jp.gr.java_conf.saka.reversi.game.ReversiColor;
import jp.gr.java_conf.saka.reversi.game.ReversiPosition;

public interface IReversiPlayer {

  String type();

  IReversiPlayer init(ReversiColor playerColor);

  ReversiPosition think(IReadOnlyReversiContext context);
}
