package jp.gr.java_conf.saka.reversi.game.player.impl;

import java.util.function.Supplier;
import jp.gr.java_conf.saka.reversi.game.player.IReversiPlayer;
import jp.gr.java_conf.saka.reversi.game.player.IReversiPlayerFactory;

public class ReversiPlayerFactoryGeneralImpl implements IReversiPlayerFactory {

  private String type;
  private Supplier<IReversiPlayer> playerSupplier;

  public static IReversiPlayerFactory newInstance(Supplier<IReversiPlayer> playerSupplier) {
    return new ReversiPlayerFactoryGeneralImpl(playerSupplier.get().type(), playerSupplier);
  }

  ReversiPlayerFactoryGeneralImpl(String type,
      Supplier<IReversiPlayer> playerSupplier) {
    this.type = type;
    this.playerSupplier = playerSupplier;
  }

  @Override
  public String type() {
    return type;
  }

  @Override
  public IReversiPlayer newPlayer() {
    return playerSupplier.get();
  }
}
