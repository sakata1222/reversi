package jp.gr.java_conf.saka.reversi.player.impl.com.mcts;

import java.util.Collections;
import java.util.List;
import jp.gr.java_conf.saka.fw.game.base.GamePlayerColor;
import jp.gr.java_conf.saka.fw.game.com.mcts.IMctsPlayOutExecutor;
import jp.gr.java_conf.saka.reversi.game.ReversiColor;
import jp.gr.java_conf.saka.reversi.game.ReversiGame;
import jp.gr.java_conf.saka.reversi.game.ReversiPosition;
import jp.gr.java_conf.saka.reversi.game.ReversiResult;
import jp.gr.java_conf.saka.reversi.game.ReversiResult.ReversiResultType;
import org.apache.commons.collections4.CollectionUtils;

public class MctsReversiRandomPlayOutExecutor implements
    IMctsPlayOutExecutor<MctsReversiGame, MctsReversiMove> {

  private ReversiColor playerColor;

  MctsReversiRandomPlayOutExecutor(ReversiColor playerColor) {
    this.playerColor = playerColor;
  }

  @Override
  public PlayOutResult playOut(MctsReversiGame game, GamePlayerColor nextPlayer) {
    ReversiGame actualGame = game.getGame();
    ReversiGame clonedGame = actualGame.clone();
    GamePlayerColor currentColor = nextPlayer;
    while (!clonedGame.getResult().isGameEnd()) {
      List<ReversiPosition> positionList = clonedGame
          .puttablePositions(MctsReversiColorDictionary.resolve(currentColor));
      if (CollectionUtils.isNotEmpty(positionList)) {
        Collections.shuffle(positionList);
        clonedGame.put(positionList.get(0), MctsReversiColorDictionary.resolve(currentColor));
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
