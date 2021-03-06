package jp.gr.java_conf.saka.reversi.simulator;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.IntStream;
import jp.gr.java_conf.saka.reversi.game.ReversiGameMaster;
import jp.gr.java_conf.saka.reversi.game.base.ReversiResult;
import jp.gr.java_conf.saka.reversi.game.base.ReversiResult.ReversiResultType;
import jp.gr.java_conf.saka.reversi.game.player.IReversiPlayer;
import jp.gr.java_conf.saka.reversi.game.player.IReversiPlayerFactory;
import jp.gr.java_conf.saka.reversi.game.player.ReversiPlayerManager;
import jp.gr.java_conf.saka.reversi.game.view.empty.ReversiEmptyViewer;

public class ReversiWinRateSimulator {

  private IReversiSimulatorViewer viewer;
  private boolean parallelExecution;

  public static ReversiWinRateSimulator newInstance(IReversiSimulatorViewer viewer) {
    return new ReversiWinRateSimulator(viewer, true);
  }

  public ReversiWinRateSimulator(
      IReversiSimulatorViewer viewer, boolean parallelExecution) {
    this.viewer = viewer;
    this.parallelExecution = parallelExecution;
  }

  public void simulate(ReversiPlayerManager playerManager) {
    ReversiSimulateTargets targets = viewer.selectTargets(playerManager);
    IReversiPlayerFactory playerAFactory = targets.getPlayerAFactory();
    IReversiPlayerFactory playerBFactory = targets.getPlayerBFactory();
    int numOfTests = viewer.numOfTests();

    ReversiWinRateSimulateResult result = new ReversiWinRateSimulateResult(playerAFactory.type(),
        playerBFactory.type());
    AtomicBoolean isPlayerAFirst = new AtomicBoolean(true);
    createSimulationStream(numOfTests).forEach(i -> {
      boolean localIsPlayerAFirst = isPlayerAFirst.getAndSet(!isPlayerAFirst.get());
      ReversiGameMaster game = newGame(localIsPlayerAFirst, playerAFactory.newPlayer(),
          playerBFactory.newPlayer());
      ReversiResult gameResult = game.start();
      addResult(localIsPlayerAFirst, gameResult, result);
      viewer.viewProgress(i, result);
    });
    viewer.viewResult(result);
  }

  private IntStream createSimulationStream(int numOfTests) {
    IntStream simulationStream = IntStream.range(0, numOfTests);
    if (parallelExecution) {
      return simulationStream.parallel();
    } else {
      return simulationStream.sequential();
    }
  }

  private ReversiGameMaster newGame(boolean isPlayerAFirst, IReversiPlayer playerA,
      IReversiPlayer playerB) {
    IReversiPlayer black;
    IReversiPlayer white;
    if (isPlayerAFirst) {
      black = playerA;
      white = playerB;
    } else {
      black = playerB;
      white = playerA;
    }
    return ReversiGameMaster.newGame(new ReversiEmptyViewer(), black, white, 0);
  }

  private void addResult(boolean isPlayerAFirst, ReversiResult gameResult,
      ReversiWinRateSimulateResult result) {
    if (gameResult.getResultType() == ReversiResultType.BLACK_WON) {
      if (isPlayerAFirst) {
        result.playerAWon();
      } else {
        result.playerBWon();
      }
    } else if (gameResult.getResultType() == ReversiResultType.WHITE_WON) {
      if (isPlayerAFirst) {
        result.playerBWon();
      } else {
        result.playerAWon();
      }
    } else {
      result.draw();
    }
  }
}
