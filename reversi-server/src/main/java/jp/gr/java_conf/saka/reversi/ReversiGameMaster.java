package jp.gr.java_conf.saka.reversi;

import java.util.HashMap;
import java.util.Map;
import jp.gr.java_conf.saka.reversi.game.ReversiColor;
import jp.gr.java_conf.saka.reversi.game.ReversiGame;
import jp.gr.java_conf.saka.reversi.game.ReversiPlayContext;
import jp.gr.java_conf.saka.reversi.game.ReversiPosition;
import jp.gr.java_conf.saka.reversi.game.ReversiResult;
import jp.gr.java_conf.saka.reversi.player.IReversiPlayer;
import jp.gr.java_conf.saka.reversi.view.IReversiViewer;

public class ReversiGameMaster {

  private ReversiGame game;
  private IReversiViewer viewer;
  private Map<ReversiColor, IReversiPlayer> playerMap;

  static ReversiGameMaster newGame(IReversiViewer viewer, IReversiPlayer blackPlayer,
      IReversiPlayer whitePlayer) {
    viewer.init();
    Map<ReversiColor, IReversiPlayer> map = new HashMap<>();
    map.put(ReversiColor.BLACK, blackPlayer.init(ReversiColor.BLACK));
    map.put(ReversiColor.WHITE, whitePlayer.init(ReversiColor.WHITE));
    return new ReversiGameMaster(ReversiGame.newGame(), viewer, map);
  }

  ReversiGameMaster(ReversiGame game, IReversiViewer viewer,
      Map<ReversiColor, IReversiPlayer> playerMap) {
    this.game = game;
    this.viewer = viewer;
    this.playerMap = playerMap;
  }

  void start() {
    ReversiColor currentColor = ReversiColor.BLACK;
    IReversiPlayer currentPlayer = playerMap.get(currentColor);
    ReversiPlayContext context = ReversiPlayContext.fixThinkingTime(game, 20);
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
      result = game.getResult();
      currentColor = nextColor(currentColor);
      currentPlayer = playerMap.get(currentColor);
    }
    viewer.displayMessage("Game is End.");
    viewer.view(game.getClonedBoard());
    viewer.gameEnd(result);
    viewer.destroy();
  }

  private ReversiColor nextColor(ReversiColor current) {
    if (current == ReversiColor.WHITE) {
      return ReversiColor.BLACK;
    } else {
      return ReversiColor.WHITE;
    }
  }
}
