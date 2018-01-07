package jp.gr.java_conf.saka.reversi;

import jp.gr.java_conf.saka.reversi.game.player.ReversiPlayerManager;
import jp.gr.java_conf.saka.reversi.simulator.ReversiWinRateSimulator;
import jp.gr.java_conf.saka.reversi.simulator.view.cli.ReversiSimulatorViewerCliImpl;

public class ReversiSimulateMain {

  public static void main(String[] args) {
    ReversiWinRateSimulator simulator = ReversiWinRateSimulator
        .newInstance(ReversiSimulatorViewerCliImpl.newDefaultInstance());
    simulator.simulate(ReversiPlayerManager.newInstanceForSimulate());
  }
}
