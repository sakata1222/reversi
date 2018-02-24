package jp.gr.java_conf.saka.reversi.game.player;

import static jp.gr.java_conf.saka.reversi.game.player.ReversiPlayers.customAlphaBeta;
import static jp.gr.java_conf.saka.reversi.game.player.ReversiPlayers.customAlphaBetaWithSort;
import static jp.gr.java_conf.saka.reversi.game.player.ReversiPlayers.fixedPieceBasedAlphaBeta;
import static jp.gr.java_conf.saka.reversi.game.player.ReversiPlayers.fixedPieceBasedAlphaBetaWithSort;
import static jp.gr.java_conf.saka.reversi.game.player.ReversiPlayers.mcts;
import static jp.gr.java_conf.saka.reversi.game.player.ReversiPlayers.mctsReuse;
import static jp.gr.java_conf.saka.reversi.game.player.ReversiPlayers.random;
import static jp.gr.java_conf.saka.reversi.game.player.ReversiPlayers.simpleAlphaBeta;

import com.google.common.collect.ImmutableList;
import java.util.ArrayList;
import java.util.List;
import jp.gr.java_conf.saka.reversi.game.player.impl.ReversiPlayerFactoryGeneralImpl;

public class ReversiPlayerManager {

  private List<IReversiPlayerFactory> playerFactories;

  public static ReversiPlayerManager newInstanceForSimulate() {
    return new ReversiPlayerManager(ImmutableList.of(
        ReversiPlayerFactoryGeneralImpl.newInstance(() -> random()),
        ReversiPlayerFactoryGeneralImpl.newInstance(() -> mcts(500)),
        ReversiPlayerFactoryGeneralImpl.newInstance(() -> mcts(1000)),
        ReversiPlayerFactoryGeneralImpl.newInstance(() -> mcts(2000)),
        ReversiPlayerFactoryGeneralImpl.newInstance(() -> mctsReuse(500)),
        ReversiPlayerFactoryGeneralImpl.newInstance(() -> mctsReuse(1000)),
        ReversiPlayerFactoryGeneralImpl.newInstance(() -> mctsReuse(2000)),
        ReversiPlayerFactoryGeneralImpl.newInstance(() -> simpleAlphaBeta(1)),
        ReversiPlayerFactoryGeneralImpl.newInstance(() -> simpleAlphaBeta(2)),
        ReversiPlayerFactoryGeneralImpl.newInstance(() -> simpleAlphaBeta(3)),
        ReversiPlayerFactoryGeneralImpl.newInstance(() -> simpleAlphaBeta(4)),
        ReversiPlayerFactoryGeneralImpl.newInstance(() -> simpleAlphaBeta(5)),
        ReversiPlayerFactoryGeneralImpl.newInstance(() -> fixedPieceBasedAlphaBeta(1)),
        ReversiPlayerFactoryGeneralImpl.newInstance(() -> fixedPieceBasedAlphaBeta(2)),
        ReversiPlayerFactoryGeneralImpl.newInstance(() -> fixedPieceBasedAlphaBeta(3)),
        ReversiPlayerFactoryGeneralImpl.newInstance(() -> fixedPieceBasedAlphaBeta(4)),
        ReversiPlayerFactoryGeneralImpl.newInstance(() -> fixedPieceBasedAlphaBeta(5)),
        ReversiPlayerFactoryGeneralImpl.newInstance(() -> fixedPieceBasedAlphaBeta(6)),
        ReversiPlayerFactoryGeneralImpl.newInstance(() -> fixedPieceBasedAlphaBetaWithSort(5)),
        ReversiPlayerFactoryGeneralImpl.newInstance(() -> fixedPieceBasedAlphaBetaWithSort(6)),
        ReversiPlayerFactoryGeneralImpl.newInstance(() -> customAlphaBetaWithSort(5)),
        ReversiPlayerFactoryGeneralImpl.newInstance(() -> customAlphaBetaWithSort(6)),
        ReversiPlayerFactoryGeneralImpl.newInstance(() -> customAlphaBeta(5)),
        ReversiPlayerFactoryGeneralImpl.newInstance(() -> customAlphaBeta(6)),
        ReversiPlayerFactoryGeneralImpl.newInstance(() -> ReversiPlayers.custom(5)),
        ReversiPlayerFactoryGeneralImpl.newInstance(() -> ReversiPlayers.custom(6))
    ));
  }

  ReversiPlayerManager(
      List<IReversiPlayerFactory> playerFactories) {
    this.playerFactories = playerFactories;
  }

  public List<IReversiPlayerFactory> playerFactories() {
    return new ArrayList<>(playerFactories);
  }
}
