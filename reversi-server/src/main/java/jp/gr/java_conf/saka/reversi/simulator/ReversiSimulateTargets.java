package jp.gr.java_conf.saka.reversi.simulator;

import jp.gr.java_conf.saka.reversi.game.player.IReversiPlayerFactory;

public class ReversiSimulateTargets {

  private IReversiPlayerFactory playerAFactory;
  private IReversiPlayerFactory playerBFactory;

  public ReversiSimulateTargets(
      IReversiPlayerFactory playerAFactory,
      IReversiPlayerFactory playerBFactory) {
    this.playerAFactory = playerAFactory;
    this.playerBFactory = playerBFactory;
  }

  IReversiPlayerFactory getPlayerAFactory() {
    return playerAFactory;
  }

  IReversiPlayerFactory getPlayerBFactory() {
    return playerBFactory;
  }
}
