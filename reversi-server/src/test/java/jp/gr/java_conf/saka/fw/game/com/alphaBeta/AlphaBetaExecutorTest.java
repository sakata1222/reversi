package jp.gr.java_conf.saka.fw.game.com.alphaBeta;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import jp.gr.java_conf.saka.fw.game.base.GamePlayerColor;
import jp.gr.java_conf.saka.fw.game.base.IGame;
import jp.gr.java_conf.saka.fw.game.base.IGameMove;
import jp.gr.java_conf.saka.fw.game.base.IGameStatusEvaluateFunction;
import org.junit.Test;

public class AlphaBetaExecutorTest {

  @Test
  public void executeSample1() {
    // http://uguisu.skr.jp/othello/alpha-beta.html
    DummyMove s = new DummyMove("s");

    DummyMove a = new DummyMove("a");
    DummyMove b = new DummyMove("b");

    DummyMove c = new DummyMove("c");

    DummyMove c1 = new DummyMove("c1");
    DummyMove c2 = new DummyMove("c2");
    DummyMove c3 = new DummyMove("c3");

    DummyMove d = new DummyMove("d");
    DummyMove d1 = new DummyMove("d1");
    DummyMove d2 = new DummyMove("d2");
    DummyMove d3 = new DummyMove("d3");

    DummyMove e = new DummyMove("e");
    DummyMove e1 = new DummyMove("e1");
    DummyMove e2 = new DummyMove("e2");
    DummyMove e3 = new DummyMove("e3");

    DummyMove f = new DummyMove("f");
    DummyMove f1 = new DummyMove("f1");
    DummyMove f2 = new DummyMove("f2");
    DummyMove f3 = new DummyMove("f3");

    DummyGame game = new DummyGame();

    Map<DummyMove, List<DummyMove>> puttableMoveMap = ImmutableMap.<DummyMove, List<DummyMove>>builder()
        .put(s, ImmutableList.of(a, b))//
        .put(a, ImmutableList.of(c, d))//
        .put(b, ImmutableList.of(e, f))//
        .put(c, ImmutableList.of(c1, c2, c3))//
        .put(d, ImmutableList.of(d1, d2, d3))//
        .put(e, ImmutableList.of(e1, e2, e3))//
        .put(f, ImmutableList.of(f1, f2, f3))//
        .build();

    Map<DummyMove, Long> evalMap = ImmutableMap.<DummyMove, Long>builder()
        .put(c1, 2L)
        .put(c2, 4L)
        .put(c3, 5L)
        .put(d1, 7L)
        .put(d2, 3L)
        .put(d3, 5L)
        .put(e1, 4L)
        .put(e2, 3L)
        .put(e3, 2L)
        .put(f1, 6L)
        .put(f2, 4L)
        .put(f3, 1L)
        .build();

    DummyGameEvaluator eval = new DummyGameEvaluator();
    eval.evalMap = evalMap;
    game.puttableMoveMap = puttableMoveMap;
    game.moves.add(s);
    AlphaBetaExecutor<DummyGame, DummyMove> exec = new AlphaBetaExecutor<>(//
        GamePlayerColor.FIRST,
        eval,
        Optional.empty(), 3);

    DummyMove move = exec.execute(game);
    assertThat(move, sameInstance(a));
    assertThat(eval.evaluated, is(ImmutableList.of(c1, c2, c3, d1, e1, e2, e3)));
  }

  @Test
  public void executeSample2() {
    // http://aidiary.hatenablog.com/entry/20050205/1274150331
    DummyMove n1 = new DummyMove("n1");

    DummyMove n2 = new DummyMove("n2");
    DummyMove n3 = new DummyMove("n3");

    DummyMove n4 = new DummyMove("n4");
    DummyMove n5 = new DummyMove("n5");
    DummyMove n6 = new DummyMove("n6");
    DummyMove n7 = new DummyMove("n7");
    DummyGame game = new DummyGame();

    Map<DummyMove, List<DummyMove>> puttableMoveMap = ImmutableMap.<DummyMove, List<DummyMove>>builder()
        .put(n1, ImmutableList.of(n2, n3))//
        .put(n2, ImmutableList.of(n4, n5))//
        .put(n3, ImmutableList.of(n6, n7))//
        .build();

    Map<DummyMove, Long> evalMap = ImmutableMap.<DummyMove, Long>builder()
        .put(n4, 8L)
        .put(n5, 10L)
        .put(n6, 2L)
        .build();

    DummyGameEvaluator eval = new DummyGameEvaluator();
    eval.evalMap = evalMap;
    game.puttableMoveMap = puttableMoveMap;
    game.moves.add(n1);
    AlphaBetaExecutor<DummyGame, DummyMove> exec = new AlphaBetaExecutor<>(//
        GamePlayerColor.FIRST,
        eval,
        Optional.empty(), 3);
    DummyMove move = exec.execute(game);
    assertThat(exec.getRoot().getAlphaValue(), is(8L));
    assertThat(exec.getRoot().getChildren().get(1).getLastMove(), sameInstance(n3));
    assertThat(exec.getRoot().getChildren().get(1).getBetaValue(), is(2L));
    assertThat(move, sameInstance(n2));
    assertThat(eval.evaluated, is(ImmutableList.of(n4, n5, n6)));
  }

  @Test
  public void executeSample3() {
    // http://aidiary.hatenablog.com/entry/20050205/1274150331
    DummyMove n2 = new DummyMove("n2");
    DummyMove n3 = new DummyMove("n3");

    DummyMove n6 = new DummyMove("n6");
    DummyMove n7 = new DummyMove("n7");

    DummyMove n8 = new DummyMove("n8");
    DummyMove n9 = new DummyMove("n9");
    DummyMove n10 = new DummyMove("n10");
    DummyMove n11 = new DummyMove("n11");
    DummyGame game = new DummyGame();

    Map<DummyMove, List<DummyMove>> puttableMoveMap = ImmutableMap.<DummyMove, List<DummyMove>>builder()
        .put(n2, ImmutableList.of(n3))//
        .put(n3, ImmutableList.of(n6, n7))//
        .put(n6, ImmutableList.of(n8, n9))//
        .put(n7, ImmutableList.of(n10, n11))//
        .build();

    Map<DummyMove, Long> evalMap = ImmutableMap.<DummyMove, Long>builder()
        .put(n8, 2L)
        .put(n9, -1L)
        .put(n10, 10L)
        .put(n11, 2L)
        .build();

    DummyGameEvaluator eval = new DummyGameEvaluator();
    eval.evalMap = evalMap;
    game.puttableMoveMap = puttableMoveMap;
    game.moves.add(n2);
    AlphaBetaExecutor<DummyGame, DummyMove> exec = new AlphaBetaExecutor<>(//
        GamePlayerColor.SECOND,
        eval,
        Optional.empty(), 4);
    DummyMove move = exec.execute(game);
    AlphaBetaGameNode<DummyGame, DummyMove> resultN3 = exec.getRoot().getChildren().get(0);
    assertThat(resultN3.getLastMove(), sameInstance(n3));
    assertThat(resultN3.getBetaValue(), is(2L));
    AlphaBetaGameNode<DummyGame, DummyMove> resultN7 = resultN3.getChildren().get(1);
    assertThat(resultN7.getLastMove(), sameInstance(n7));
    assertThat(resultN7.getAlphaValue(), is(10L));
    assertThat(eval.evaluated, is(ImmutableList.of(n8, n9, n10)));
  }

  static class DummyGameEvaluator implements IGameStatusEvaluateFunction<DummyGame> {

    Map<DummyMove, Long> evalMap;
    private List<DummyMove> evaluated = new ArrayList<>();

    @Override
    public long evaluate(GamePlayerColor color, DummyGame game) {
      DummyMove lastMove = game.moves.get(game.moves.size() - 1);
      evaluated.add(lastMove);
      return evalMap.get(lastMove);
    }
  }

  static class DummyGame implements IGame<DummyMove>, Cloneable {

    Map<DummyMove, List<DummyMove>> puttableMoveMap;

    List<DummyMove> moves = new ArrayList<>();

    @Override
    public List<DummyMove> puttableMoves(GamePlayerColor color) {
      return puttableMoveMap.get(moves.get(moves.size() - 1));
    }

    @Override
    public IGame clone() {
      try {
        DummyGame clone = (DummyGame) super.clone();
        clone.moves = new ArrayList<>(moves);
        return clone;
      } catch (CloneNotSupportedException e) {
        throw new RuntimeException(e);
      }
    }

    @Override
    public void put(DummyMove move, GamePlayerColor childColor) {
      moves.add(move);
    }

    @Override
    public boolean isSameState(IGame<DummyMove> otherGame) {
      return false;
    }
  }

  static class DummyMove implements IGameMove {

    String moveName;

    DummyMove(String moveName) {
      this.moveName = moveName;
    }

    @Override
    public String toString() {
      return "DummyMove{" +
          "moveName='" + moveName + '\'' +
          '}';
    }
  }
}
