package jp.gr.java_conf.saka.reversi.game.player;

import java.util.Optional;
import java.util.function.Supplier;
import jp.gr.java_conf.saka.reversi.game.player.impl.com.alphaBeta.AlphaBetaReversiCandidateComparators;
import jp.gr.java_conf.saka.reversi.game.player.impl.com.alphaBeta.AlphaBetaReversiPlayer;
import jp.gr.java_conf.saka.reversi.game.player.impl.com.custom.ReversiCustomPlayer;
import jp.gr.java_conf.saka.reversi.game.player.impl.com.fw.eval.ReversiStatusEvaluationFunctionCustomImpl;
import jp.gr.java_conf.saka.reversi.game.player.impl.com.fw.eval.ReversiStatusEvaluationFunctionNumOfFixedPieceImpl;
import jp.gr.java_conf.saka.reversi.game.player.impl.com.fw.eval.ReversiStatusEvaluationFunctionNumOfPieceImpl;
import jp.gr.java_conf.saka.reversi.game.player.impl.com.mcts.ReversiMctsPlayer;
import jp.gr.java_conf.saka.reversi.game.player.impl.com.mcts.ReversiMctsReusePlayer;
import jp.gr.java_conf.saka.reversi.game.player.impl.com.random.ReversiRandomPlayer;
import jp.gr.java_conf.saka.reversi.game.player.impl.human.ReversiHumanPlayer;

public class ReversiPlayers {

  public static IReversiPlayer human(
      Supplier<IReversiPositionInput> inputSupplier) {
    return ReversiHumanPlayer.newHumanPlayer(inputSupplier);
  }

  public static IReversiPlayer random() {
    return ReversiRandomPlayer.randomPlayer();
  }

  public static IReversiPlayer mcts(int maxTotalTries) {
    return ReversiMctsPlayer.mctsPlayer(maxTotalTries);
  }

  public static IReversiPlayer mctsReuse(int maxTotalTries) {
    return ReversiMctsReusePlayer.mctsReusePlayer(maxTotalTries);
  }

  public static IReversiPlayer simpleAlphaBeta(int depth) {
    return AlphaBetaReversiPlayer
        .newInstance(new ReversiStatusEvaluationFunctionNumOfPieceImpl(), Optional.empty(), depth);
  }

  public static IReversiPlayer fixedPieceBasedAlphaBeta(int depth) {
    return AlphaBetaReversiPlayer
        .newInstance(new ReversiStatusEvaluationFunctionNumOfFixedPieceImpl(), Optional.empty(),
            depth);
  }

  public static IReversiPlayer fixedPieceBasedAlphaBetaWithSort(int depth) {
    return AlphaBetaReversiPlayer
        .newInstance(new ReversiStatusEvaluationFunctionNumOfFixedPieceImpl(), Optional.of(
            AlphaBetaReversiCandidateComparators.newDefaultComparator()),
            depth);
  }

  public static IReversiPlayer customAlphaBetaWithSort(int depth) {
    return AlphaBetaReversiPlayer
        .newInstance(new ReversiStatusEvaluationFunctionCustomImpl(), Optional.of(
            AlphaBetaReversiCandidateComparators.newDefaultComparator()),
            depth);
  }

  public static IReversiPlayer customAlphaBeta(int depth) {
    return AlphaBetaReversiPlayer
        .newInstance(new ReversiStatusEvaluationFunctionCustomImpl(), Optional.empty(),
            depth);
  }

  public static IReversiPlayer custom() {
    return ReversiCustomPlayer.newInstance(5);
  }

  public static IReversiPlayer custom(int depth) {
    return ReversiCustomPlayer.newInstance(depth);
  }
}
