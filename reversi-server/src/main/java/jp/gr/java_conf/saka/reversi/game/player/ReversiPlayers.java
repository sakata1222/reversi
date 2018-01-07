package jp.gr.java_conf.saka.reversi.game.player;

import java.util.function.Supplier;
import jp.gr.java_conf.saka.reversi.game.player.impl.com.mcts.ReversiMctsPlayer;
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
}
