package jp.gr.java_conf.saka.reversi.game.player;

public interface IReversiPlayerFactory {

  String type();

  IReversiPlayer newPlayer();
}
