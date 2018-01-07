package jp.gr.java_conf.saka.reversi.game.player.impl.com.mcts;

import java.util.List;
import java.util.stream.Collectors;
import jp.gr.java_conf.saka.fw.game.base.GamePlayerColor;
import jp.gr.java_conf.saka.fw.game.com.mcts.IMctsGame;
import jp.gr.java_conf.saka.reversi.game.base.ReversiGame;

public class MctsReversiGame implements
    IMctsGame<MctsReversiMove> {

  private ReversiGame game;

  static MctsReversiGame wrap(ReversiGame game) {
    return new MctsReversiGame(game);
  }

  private MctsReversiGame(ReversiGame game) {
    this.game = game;
  }

  @Override
  public List<MctsReversiMove> puttableMoves(
      GamePlayerColor color) {
    return game.puttablePositions(MctsReversiColorDictionary.resolve(color)).stream()
        .map(pos -> MctsReversiMove.wrap(pos))
        .collect(Collectors.toList());
  }

  @Override
  public IMctsGame clone() {
    return new MctsReversiGame(game.clone());
  }

  @Override
  public void put(MctsReversiMove move,
      GamePlayerColor childColor) {
    game.put(move.getPosition(), MctsReversiColorDictionary.resolve(childColor));
  }

  ReversiGame getGame() {
    return game;
  }
}
