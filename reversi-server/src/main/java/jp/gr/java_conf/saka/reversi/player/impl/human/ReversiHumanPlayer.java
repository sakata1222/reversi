package jp.gr.java_conf.saka.reversi.player.impl.human;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;
import java.util.function.Supplier;
import jp.gr.java_conf.saka.reversi.game.IReadOnlyReversiContext;
import jp.gr.java_conf.saka.reversi.game.ReversiColor;
import jp.gr.java_conf.saka.reversi.game.ReversiGame;
import jp.gr.java_conf.saka.reversi.game.ReversiPosition;
import jp.gr.java_conf.saka.reversi.player.IReversiPlayer;
import jp.gr.java_conf.saka.reversi.player.IReversiPositionInput;

public class ReversiHumanPlayer implements IReversiPlayer {

  private ReversiColor playerColor;
  private Supplier<IReversiPositionInput> inputSupplier;
  private IReversiPositionInput input;

  public static IReversiPlayer newHumanPlayer(Supplier<IReversiPositionInput> inputSupplier) {
    return new ReversiHumanPlayer(inputSupplier);
  }

  ReversiHumanPlayer(
      Supplier<IReversiPositionInput> inputSupplier) {
    this.inputSupplier = inputSupplier;
  }

  @Override
  public String type() {
    return "Human";
  }

  @Override
  public IReversiPlayer init(ReversiColor playerColor) {
    this.playerColor = playerColor;
    input = inputSupplier.get();
    return this;
  }

  @Override
  public ReversiPosition think(IReadOnlyReversiContext context) {
    ReversiGame game = context.getClonedGame();
    Set<ReversiPosition> puttableSet = new HashSet<>(game.puttablePositions(playerColor));
    Predicate<ReversiPosition> isPuttable = p -> puttableSet.contains(p);
    ReversiPosition pos = input.waitAndGet(context.getMaxThinkingTimeInSec());
    while (!isPuttable.test(pos)) {
      pos = input.waitAndGet(context.getMaxThinkingTimeInSec()); // TODO wait time is not collect.
    }
    return pos;
  }
}
