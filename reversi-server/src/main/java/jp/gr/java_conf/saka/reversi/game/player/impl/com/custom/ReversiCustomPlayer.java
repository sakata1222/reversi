package jp.gr.java_conf.saka.reversi.game.player.impl.com.custom;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import jp.gr.java_conf.saka.reversi.game.base.IReadOnlyReversiContext;
import jp.gr.java_conf.saka.reversi.game.base.ReversiColor;
import jp.gr.java_conf.saka.reversi.game.base.ReversiGame;
import jp.gr.java_conf.saka.reversi.game.base.ReversiPosition;
import jp.gr.java_conf.saka.reversi.game.player.IReversiPlayer;
import jp.gr.java_conf.saka.reversi.game.player.impl.com.alphaBeta.AlphaBetaReversiPlayer;
import jp.gr.java_conf.saka.reversi.game.player.impl.com.fw.eval.ReversiStatusEvaluationFunctionCustomImpl;
import jp.gr.java_conf.saka.reversi.game.player.impl.com.fw.eval.ReversiStatusEvaluationFunctionNumOfPieceImpl;

public class ReversiCustomPlayer implements IReversiPlayer {

  private ReversiColor playerColor;
  private IReversiPlayer openingPlayer;
  private IReversiPlayer endGamePlayer;
  private int endGameTurn;

  public static ReversiCustomPlayer newInstance(int openingDepth) {
    int endGameTurn = 12;
    return new ReversiCustomPlayer(//
        AlphaBetaReversiPlayer
            .newInstance(new ReversiStatusEvaluationFunctionCustomImpl(), Optional.empty(),
                openingDepth),
        AlphaBetaReversiPlayer
            .newInstance(new ReversiStatusEvaluationFunctionNumOfPieceImpl(), Optional.empty(),
                endGameTurn),
        endGameTurn);
  }

  ReversiCustomPlayer(IReversiPlayer openingPlayer,
      IReversiPlayer endGamePlayer, int endGameTurn) {
    this.openingPlayer = openingPlayer;
    this.endGamePlayer = endGamePlayer;
    this.endGameTurn = endGameTurn;
  }

  @Override
  public String type() {
    return "Custom:" + openingPlayer.type();
  }

  @Override
  public IReversiPlayer init(ReversiColor playerColor) {
    this.playerColor = playerColor;
    this.openingPlayer.init(playerColor);
    this.endGamePlayer.init(playerColor);
    return this;
  }

  @Override
  public ReversiPosition think(IReadOnlyReversiContext context) {
    ReversiGame game = context.getClonedGame();
    return selectPlayer(game).think(context);
  }

  private IReversiPlayer selectPlayer(ReversiGame game) {
    Map<ReversiColor, AtomicInteger> count = game.countNumOfPieces();
    int total = count.values().stream().mapToInt(AtomicInteger::intValue).sum();
    int empty = game.getSize() * game.getSize() - total;
    if (empty <= endGameTurn) {
      return endGamePlayer;
    } else {
      return openingPlayer;
    }
  }
}
