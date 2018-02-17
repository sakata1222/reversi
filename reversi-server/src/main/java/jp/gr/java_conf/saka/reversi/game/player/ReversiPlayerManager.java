package jp.gr.java_conf.saka.reversi.game.player;

import com.google.common.collect.ImmutableList;
import java.util.ArrayList;
import java.util.List;
import jp.gr.java_conf.saka.reversi.game.player.impl.ReversiPlayerFactoryGeneralImpl;

public class ReversiPlayerManager {

  private List<IReversiPlayerFactory> playerFactories;

  public static ReversiPlayerManager newInstanceForSimulate() {
    return new ReversiPlayerManager(ImmutableList.of(
        ReversiPlayerFactoryGeneralImpl.newInstance(() -> ReversiPlayers.random()),
        ReversiPlayerFactoryGeneralImpl.newInstance(() -> ReversiPlayers.mcts(500)),
        ReversiPlayerFactoryGeneralImpl.newInstance(() -> ReversiPlayers.mcts(1000)),
        ReversiPlayerFactoryGeneralImpl.newInstance(() -> ReversiPlayers.mcts(2000)),
        ReversiPlayerFactoryGeneralImpl.newInstance(() -> ReversiPlayers.mctsReuse(500)),
        ReversiPlayerFactoryGeneralImpl.newInstance(() -> ReversiPlayers.mctsReuse(1000)),
        ReversiPlayerFactoryGeneralImpl.newInstance(() -> ReversiPlayers.mctsReuse(2000)),
        ReversiPlayerFactoryGeneralImpl.newInstance(() -> ReversiPlayers.simpleAlphaBeta(1)),
        ReversiPlayerFactoryGeneralImpl.newInstance(() -> ReversiPlayers.simpleAlphaBeta(2)),
        ReversiPlayerFactoryGeneralImpl.newInstance(() -> ReversiPlayers.simpleAlphaBeta(3)),
        ReversiPlayerFactoryGeneralImpl.newInstance(() -> ReversiPlayers.simpleAlphaBeta(4)),
        ReversiPlayerFactoryGeneralImpl.newInstance(() -> ReversiPlayers.simpleAlphaBeta(5)),
        ReversiPlayerFactoryGeneralImpl
            .newInstance(() -> ReversiPlayers.fixedPieceBasedAlphaBeta(1)),
        ReversiPlayerFactoryGeneralImpl
            .newInstance(() -> ReversiPlayers.fixedPieceBasedAlphaBeta(2)),
        ReversiPlayerFactoryGeneralImpl
            .newInstance(() -> ReversiPlayers.fixedPieceBasedAlphaBeta(3)),
        ReversiPlayerFactoryGeneralImpl
            .newInstance(() -> ReversiPlayers.fixedPieceBasedAlphaBeta(4)),
        ReversiPlayerFactoryGeneralImpl
            .newInstance(() -> ReversiPlayers.fixedPieceBasedAlphaBeta(5))

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
