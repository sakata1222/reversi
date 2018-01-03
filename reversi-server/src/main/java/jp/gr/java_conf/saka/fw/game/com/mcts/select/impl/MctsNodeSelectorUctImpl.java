package jp.gr.java_conf.saka.fw.game.com.mcts.select.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import jp.gr.java_conf.saka.fw.game.com.mcts.IMctsGame;
import jp.gr.java_conf.saka.fw.game.com.mcts.IMctsMove;
import jp.gr.java_conf.saka.fw.game.com.mcts.MctsNode;
import jp.gr.java_conf.saka.fw.game.com.mcts.select.IMctsNodeSelector;

/**
 * UCT(UCB(Upper Confidence Bound) Applied to Tree) based node select logic.
 */
public class MctsNodeSelectorUctImpl implements IMctsNodeSelector {

  private double c;

  public static IMctsNodeSelector newDefaultInstance() {
    // this default value(0.39) is selected by
    // http://www.kochi-tech.ac.jp/library/ron/pdf/2016/13/1170339.pdf
    return new MctsNodeSelectorUctImpl(0.39);
  }

  MctsNodeSelectorUctImpl(double c) {
    this.c = c;
  }

  @Override
  public <GAME extends IMctsGame<MOVE>, MOVE extends IMctsMove> MctsNode<GAME, MOVE> select(
      List<MctsNode<GAME, MOVE>> children) {
    int childrenTotal = children.stream()
        .mapToInt(MctsNode::getTotalTries).sum();
    // copy the array because the array is shuffled and sorted in this method.
    List<MctsNode<GAME, MOVE>> copied = new ArrayList<>(children);
    // shuffle children to select children at random
    // when two or more children have max evaluation value;
    Collections.shuffle(copied);
    // sort by UTB value.
    MctsNode<GAME, MOVE> candidateNode = copied.stream()
        .sorted(Comparator.<MctsNode<?, ?>>comparingDouble(node -> {
          double ucbValue =
              node.getWinRate() + c * (Math
                  .sqrt(2.0d * Math.log((double) childrenTotal) / (double) node.getTotalTries()));
          return ucbValue;
        }).reversed()).findFirst().get();
    if (candidateNode.hasChild()) {
      // select the children recursively.
      // if stackOverFlow occurred, then rewrite without recursive call
      return select(candidateNode.getChildren());
    } else {
      return candidateNode;
    }
  }
}
