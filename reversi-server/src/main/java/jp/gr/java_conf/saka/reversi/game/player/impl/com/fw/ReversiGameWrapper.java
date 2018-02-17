package jp.gr.java_conf.saka.reversi.game.player.impl.com.fw;

import java.util.List;
import java.util.stream.Collectors;
import jp.gr.java_conf.saka.fw.game.base.GamePlayerColor;
import jp.gr.java_conf.saka.fw.game.base.IGame;
import jp.gr.java_conf.saka.reversi.game.base.ReversiGame;

public class ReversiGameWrapper implements
    IGame<GameReversiMove> {

  private ReversiGame game;

  public static ReversiGameWrapper wrap(ReversiGame game) {
    return new ReversiGameWrapper(game);
  }

  private ReversiGameWrapper(ReversiGame game) {
    this.game = game;
  }

  @Override
  public List<GameReversiMove> puttableMoves(
      GamePlayerColor color) {
    return game.puttablePositions(ReversiColorDictionary.resolve(color)).stream()
        .map(pos -> GameReversiMove.wrap(pos))
        .collect(Collectors.toList());
  }

  @Override
  public IGame clone() {
    return new ReversiGameWrapper(game.clone());
  }

  @Override
  public void put(GameReversiMove move,
      GamePlayerColor childColor) {
    game.put(move.getPosition(), ReversiColorDictionary.resolve(childColor));
  }

  @Override
  public boolean isSameState(IGame<GameReversiMove> otherGame) {
    if (ReversiGameWrapper.class != otherGame.getClass()) {
      return false;
    }
    ReversiGameWrapper castedOtherGame = ReversiGameWrapper.class.cast(otherGame);
    return this.game.isSameState(castedOtherGame.game);
  }

  public ReversiGame getGame() {
    return game;
  }
}
