package jp.gr.java_conf.saka.reversi.game;

import java.util.HashMap;
import java.util.Map;
import jp.gr.java_conf.saka.reversi.game.base.ReversiColor;
import jp.gr.java_conf.saka.reversi.game.base.ReversiGame;
import jp.gr.java_conf.saka.reversi.game.base.ReversiPlayContext;
import jp.gr.java_conf.saka.reversi.game.base.ReversiPosition;
import jp.gr.java_conf.saka.reversi.game.base.ReversiResult;
import jp.gr.java_conf.saka.reversi.game.player.IReversiPlayer;
import jp.gr.java_conf.saka.reversi.game.view.IReversiViewer;

public class ReversiGameMaster {

  private ReversiGame game;
  private IReversiViewer viewer;
  private Map<ReversiColor, IReversiPlayer> playerMap;
  private int intervalOfTurnInMillSec;

  public static ReversiGameMaster newGame(IReversiViewer viewer,
      IReversiPlayer blackPlayer,
      IReversiPlayer whitePlayer) {
    viewer.init();
    Map<ReversiColor, IReversiPlayer> map = new HashMap<>();
    map.put(ReversiColor.BLACK, blackPlayer.init(ReversiColor.BLACK));
    map.put(ReversiColor.WHITE, whitePlayer.init(ReversiColor.WHITE));
    return new ReversiGameMaster(ReversiGame.newGame(), viewer, map, 500);
  }

  public static ReversiGameMaster newGame(IReversiViewer viewer,
      IReversiPlayer blackPlayer, IReversiPlayer whitePlayer, int intervalOfTurnInMillSec) {
    viewer.init();
    Map<ReversiColor, IReversiPlayer> map = new HashMap<>();
    map.put(ReversiColor.BLACK, blackPlayer.init(ReversiColor.BLACK));
    map.put(ReversiColor.WHITE, whitePlayer.init(ReversiColor.WHITE));
    return new ReversiGameMaster(ReversiGame.newGame(), viewer, map, intervalOfTurnInMillSec);
  }

  public ReversiGameMaster(ReversiGame game, IReversiViewer viewer,
      Map<ReversiColor, IReversiPlayer> playerMap,
      int intervalOfTurnInMillSec) {
    this.game = game;
    this.viewer = viewer;
    this.playerMap = playerMap;
    this.intervalOfTurnInMillSec = intervalOfTurnInMillSec;
  }

  public ReversiResult start() {
    ReversiColor currentColor = ReversiColor.BLACK;
    IReversiPlayer currentPlayer = playerMap.get(currentColor);
    ReversiPlayContext context = ReversiPlayContext.fixedThinkingTime(game, 20);
    ReversiResult result = game.getResult();
    while (!result.isGameEnd()) {
      viewer.onTurn(currentColor);
      viewer.view(game.getClonedBoard());
      if (game.hasMove(currentColor)) {
        ReversiPosition pos = currentPlayer.think(context.readOnly());
        viewer.displayMessage("Move:" + String.valueOf(pos));
        game.put(pos, currentColor);
        viewer.put(game.getClonedBoard(), pos, currentColor);
      } else {
        viewer.displayMessage("Skip:" + currentColor);
      }
      sleep(intervalOfTurnInMillSec);
      result = game.getResult();
      currentColor = nextColor(currentColor);
      currentPlayer = playerMap.get(currentColor);
    }
    viewer.displayMessage("Game is End.");
    viewer.view(game.getClonedBoard());
    viewer.gameEnd(result);
    viewer.destroy();
    return result;
  }

  private ReversiColor nextColor(ReversiColor current) {
    if (current == ReversiColor.WHITE) {
      return ReversiColor.BLACK;
    } else {
      return ReversiColor.WHITE;
    }
  }

  private void sleep(long millsec) {
    if (millsec <= 0) {
      return;
    }
    try {
      Thread.sleep(millsec);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}
