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
        ReversiPlayerFactoryGeneralImpl.newInstance(() -> ReversiPlayers.mctsReuse(2000))
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
