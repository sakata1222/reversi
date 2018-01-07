package jp.gr.java_conf.saka.reversi.simulator;

import jp.gr.java_conf.saka.reversi.game.player.ReversiPlayerManager;

public interface IReversiSimulatorViewer {

  ReversiSimulateTargets selectTargets(ReversiPlayerManager manager);

  int numOfTests();

  void viewProgress(int numOfTests, ReversiWinRateSimulateResult result);

  void viewResult(ReversiWinRateSimulateResult result);
}
