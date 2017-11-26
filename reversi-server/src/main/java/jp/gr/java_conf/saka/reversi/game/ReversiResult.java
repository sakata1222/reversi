package jp.gr.java_conf.saka.reversi.game;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class ReversiResult {

  private boolean isGameEnd;

  private ReversiResultType resultType;

  private Map<ReversiColor, AtomicInteger> piecesCount;

  static ReversiResultBuilder builder() {
    return new ReversiResultBuilder(false, new HashMap<>());
  }

  ReversiResult(boolean isGameEnd,
      ReversiResultType resultType,
      Map<ReversiColor, AtomicInteger> piecesCount) {
    this.isGameEnd = isGameEnd;
    this.resultType = resultType;
    this.piecesCount = piecesCount;
  }

  public boolean isGameEnd() {
    return isGameEnd;
  }

  public Map<ReversiColor, AtomicInteger> getPiecesCount() {
    return new HashMap<>(piecesCount);
  }

  public ReversiResultType getResultType() {
    return resultType;
  }

  static class ReversiResultBuilder {

    ReversiResultBuilder(boolean isGameEnd,
        Map<ReversiColor, AtomicInteger> piecesCount) {
      this.isGameEnd = isGameEnd;
      this.piecesCount = piecesCount;
    }

    private boolean isGameEnd;
    private Map<ReversiColor, AtomicInteger> piecesCount;

    ReversiResultBuilder gameEnd() {
      isGameEnd = true;
      return this;
    }

    ReversiResultBuilder notGameEnd() {
      isGameEnd = false;
      return this;
    }

    ReversiResult build() {
      int black = piecesCount.getOrDefault(ReversiColor.BLACK, new AtomicInteger(0)).get();
      int white = piecesCount.getOrDefault(ReversiColor.WHITE, new AtomicInteger(0)).get();
      ReversiResultType type = null;
      if (black > white) {
        type = ReversiResultType.BLACK_WON;
      } else if (black < white) {
        type = ReversiResultType.WHITE_WON;
      } else {
        type = ReversiResultType.DRAW;
      }
      return new ReversiResult(isGameEnd, type, piecesCount);
    }

    int addPieceCount(ReversiColor color) {
      return piecesCount.computeIfAbsent(color, c -> new AtomicInteger(0)).incrementAndGet();
    }
  }

  public static enum ReversiResultType {
    DRAW {
      @Override
      public boolean isColorWon(ReversiColor color) {
        return false;
      }
    }, BLACK_WON {
      @Override
      public boolean isColorWon(ReversiColor color) {
        return ReversiColor.BLACK == color;
      }
    }, WHITE_WON {
      @Override
      public boolean isColorWon(ReversiColor color) {
        return ReversiColor.WHITE == color;
      }
    };

    public abstract boolean isColorWon(ReversiColor color);
  }
}
