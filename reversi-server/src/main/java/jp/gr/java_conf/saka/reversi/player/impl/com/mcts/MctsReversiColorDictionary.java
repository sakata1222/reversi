package jp.gr.java_conf.saka.reversi.player.impl.com.mcts;

import com.google.common.collect.ImmutableMap;
import java.util.Map;
import java.util.stream.Collectors;
import jp.gr.java_conf.saka.fw.game.base.GamePlayerColor;
import jp.gr.java_conf.saka.reversi.game.ReversiColor;

public class MctsReversiColorDictionary {

  private static final Map<GamePlayerColor, ReversiColor> DIC = ImmutableMap.<GamePlayerColor, ReversiColor>builder()
      .put(GamePlayerColor.FIRST, ReversiColor.BLACK)
      .put(GamePlayerColor.SECOND, ReversiColor.WHITE).build();

  private static final Map<ReversiColor, GamePlayerColor> REVERSE_DIC = DIC.entrySet().stream()
      .collect(
          Collectors.toMap(Map.Entry::getValue, Map.Entry::getKey));

  public static ReversiColor resolve(GamePlayerColor color) {
    return DIC.get(color);
  }

  public static GamePlayerColor resolve(ReversiColor color) {
    return REVERSE_DIC.get(color);
  }
}
