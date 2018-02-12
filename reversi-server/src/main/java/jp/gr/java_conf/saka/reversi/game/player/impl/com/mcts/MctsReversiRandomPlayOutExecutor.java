package jp.gr.java_conf.saka.reversi.game.player.impl.com.mcts;

import java.util.Collections;
import java.util.List;
import jp.gr.java_conf.saka.fw.game.base.GamePlayerColor;
import jp.gr.java_conf.saka.fw.game.com.mcts.IMctsPlayOutExecutor;
import jp.gr.java_conf.saka.reversi.game.base.ReversiColor;
import jp.gr.java_conf.saka.reversi.game.base.ReversiPosition;
import jp.gr.java_conf.saka.reversi.game.base.ReversiResult;
import jp.gr.java_conf.saka.reversi.game.base.ReversiResult.ReversiResultType;
import jp.gr.java_conf.saka.reversi.game.player.impl.com.fw.GameReversiMove;
import jp.gr.java_conf.saka.reversi.game.player.impl.com.fw.ReversiColorDictionary;
import jp.gr.java_conf.saka.reversi.game.player.impl.com.fw.ReversiGameWrapper;
import org.apache.commons.collections4.CollectionUtils;

public class MctsReversiRandomPlayOutExecutor implements
    IMctsPlayOutExecutor<ReversiGameWrapper, GameReversiMove> {

  private ReversiColor playerColor;

  MctsReversiRandomPlayOutExecutor(ReversiColor playerColor) {
    this.playerColor = playerColor;
  }

  @Override
  public PlayOutResult playOut(ReversiGameWrapper game, GamePlayerColor nextPlayer) {
    jp.gr.java_conf.saka.reversi.game.base.ReversiGame actualGame = game.getGame();
    jp.gr.java_conf.saka.reversi.game.base.ReversiGame clonedGame = actualGame.clone();
    GamePlayerColor currentColor = nextPlayer;
    while (!clonedGame.getResult().isGameEnd()) {
      List<ReversiPosition> positionList = clonedGame
          .puttablePositions(ReversiColorDictionary.resolve(currentColor));
      if (CollectionUtils.isNotEmpty(positionList)) {
        Collections.shuffle(positionList);
        clonedGame.put(positionList.get(0), ReversiColorDictionary.resolve(currentColor));
      }
      currentColor = currentColor.nextPlayer();
    }
    ReversiResult result = clonedGame.getResult();
    if (result.getResultType().isColorWon(playerColor)) {
      return PlayOutResult.WON;
    } else if (result.getResultType() == ReversiResultType.DRAW) {
      return PlayOutResult.DRAW;
    } else {
      return PlayOutResult.LOSE;
    }
  }
}
