package jp.gr.java_conf.saka.fw.game.com.mcts;

import java.util.Comparator;
import java.util.List;
import jp.gr.java_conf.saka.fw.game.base.IGame;
import jp.gr.java_conf.saka.fw.game.base.IGameMove;

public class MctsResultDecider {

  <G extends IGame<M>, M extends IGameMove> MctsNode<G, M> decide(
      List<MctsNode<G, M>> candidates) {
    return candidates.stream()
        .sorted(Comparator.<MctsNode<G, M>>comparingInt(child -> child.getTotalTries()).reversed())
        .findFirst().get();
  }
}
