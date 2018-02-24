package jp.gr.java_conf.saka.reversi.game.player.impl.com.alphaBeta;

import java.util.Comparator;
import java.util.Optional;
import jp.gr.java_conf.saka.fw.game.com.alphaBeta.AlphaBetaExecutor;
import jp.gr.java_conf.saka.reversi.game.base.IReadOnlyReversiContext;
import jp.gr.java_conf.saka.reversi.game.base.ReversiColor;
import jp.gr.java_conf.saka.reversi.game.base.ReversiPosition;
import jp.gr.java_conf.saka.reversi.game.player.IReversiPlayer;
import jp.gr.java_conf.saka.reversi.game.player.impl.com.fw.GameReversiMove;
import jp.gr.java_conf.saka.reversi.game.player.impl.com.fw.IReversiStatusEvaluationFunction;
import jp.gr.java_conf.saka.reversi.game.player.impl.com.fw.ReversiColorDictionary;
import jp.gr.java_conf.saka.reversi.game.player.impl.com.fw.ReversiGameWrapper;

public class AlphaBetaReversiPlayer implements IReversiPlayer {

  private ReversiColor playerColor;
  private IReversiStatusEvaluationFunction evaluationFunction;
  private AlphaBetaExecutor<ReversiGameWrapper, GameReversiMove> executor;
  private Optional<Comparator<GameReversiMove>> candidatesComparator;
  private int depth;

  public static AlphaBetaReversiPlayer newInstance(
      IReversiStatusEvaluationFunction evaluationFunction,
      Optional<Comparator<GameReversiMove>> candidatesComparator,
      int depth) {
    return new AlphaBetaReversiPlayer(evaluationFunction, candidatesComparator, depth);
  }

  AlphaBetaReversiPlayer(
      IReversiStatusEvaluationFunction evaluationFunction,
      Optional<Comparator<GameReversiMove>> candidatesComparator,
      int depth) {
    this.evaluationFunction = evaluationFunction;
    this.candidatesComparator = candidatesComparator;
    this.depth = depth;
  }

  @Override
  public String type() {
    return "Alpha-Beta " + evaluationFunction.getClass().getSimpleName() + " (" + depth + "), sort="
        + candidatesComparator.isPresent();
  }

  @Override
  public IReversiPlayer init(ReversiColor playerColor) {
    this.playerColor = playerColor;
    executor = AlphaBetaExecutor
        .newInstanceWithCandidateComparator(ReversiColorDictionary.resolve(playerColor),
            evaluationFunction, candidatesComparator, depth);
    return this;
  }

  @Override
  public ReversiPosition think(IReadOnlyReversiContext context) {
    GameReversiMove move = executor.execute(ReversiGameWrapper.wrap(context.getClonedGame()));
    return move.getPosition();
  }
}
