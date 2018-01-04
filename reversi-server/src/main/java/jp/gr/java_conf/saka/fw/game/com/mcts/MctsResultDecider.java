package jp.gr.java_conf.saka.fw.game.com.mcts;

import java.util.Comparator;
import java.util.List;

public class MctsResultDecider {

  <G extends IMctsGame<M>, M extends IMctsMove> MctsNode<G, M> decide(
      List<MctsNode<G, M>> candidates) {
    return candidates.stream()
        .sorted(Comparator.<MctsNode<G, M>>comparingInt(child -> child.getTotalTries()).reversed())
        .findFirst().get();
  }
}
