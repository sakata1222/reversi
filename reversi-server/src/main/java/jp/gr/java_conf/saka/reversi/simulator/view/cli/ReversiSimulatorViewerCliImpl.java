package jp.gr.java_conf.saka.reversi.simulator.view.cli;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;
import jp.gr.java_conf.saka.reversi.game.player.IReversiPlayerFactory;
import jp.gr.java_conf.saka.reversi.game.player.ReversiPlayerManager;
import jp.gr.java_conf.saka.reversi.simulator.IReversiSimulatorViewer;
import jp.gr.java_conf.saka.reversi.simulator.ReversiSimulateTargets;
import jp.gr.java_conf.saka.reversi.simulator.ReversiWinRateSimulateResult;

public class ReversiSimulatorViewerCliImpl implements IReversiSimulatorViewer {

  private PrintStream out;
  private InputStream input;
  private int intervalOfShowProgress;

  public static ReversiSimulatorViewerCliImpl newDefaultInstance() {
    return new ReversiSimulatorViewerCliImpl(System.out, System.in, 1);
  }

  public ReversiSimulatorViewerCliImpl(PrintStream out, InputStream input,
      int intervalOfShowProgress) {
    this.out = out;
    this.input = input;
    this.intervalOfShowProgress = intervalOfShowProgress;
  }

  @Override
  public ReversiSimulateTargets selectTargets(ReversiPlayerManager manager) {
    Map<String, IReversiPlayerFactory> factoryMap = manager.playerFactories().stream()
        .collect(Collectors.toMap(IReversiPlayerFactory::type, Function.identity(), (o, o2) -> {
          throw new IllegalArgumentException("type is duplicated:" + Arrays.asList(o, o2));
        }, () -> new LinkedHashMap<>()));
    AtomicInteger playerIndex = new AtomicInteger(0);
    SortedMap<Integer, String> indexToTypeMap = new TreeMap<>(
        factoryMap.keySet().stream().sequential()
            .collect(Collectors.toMap(type -> playerIndex.incrementAndGet(), Function.identity())));
    // show candidates
    indexToTypeMap.entrySet().stream().sequential().forEach(entry -> {
      out.println(entry.getKey() + ":" + entry.getValue());
    });
    // do no close this stream, because this stream wraps System.input
    BufferedReader br = new BufferedReader(
        new InputStreamReader(input, StandardCharsets.UTF_8));
    out.println("select playerA by number.");
    IReversiPlayerFactory factoryA = selectByInput(br, indexToTypeMap, factoryMap);
    out.println("select playerB by number.");
    IReversiPlayerFactory factoryB = selectByInput(br, indexToTypeMap, factoryMap);
    return new ReversiSimulateTargets(factoryA, factoryB);
  }

  private IReversiPlayerFactory selectByInput(BufferedReader br,
      SortedMap<Integer, String> indexToTypeMap, Map<String, IReversiPlayerFactory> factoryMap) {
    while (true) {
      try {
        String line = br.readLine();
        Integer playerIndex = Integer.valueOf(line);
        String playerType = indexToTypeMap.get(playerIndex);
        if (Objects.nonNull(playerType)) {
          return factoryMap.get(playerType);
        }
      } catch (NumberFormatException e) {
        out.println("Please input number.");
      } catch (IOException e) {
        throw new UncheckedIOException(e);
      }
    }
  }

  @Override
  public int numOfTests() {
    out.println("Please input number of tests");
    while (true) {
      try {
        BufferedReader br = new BufferedReader(
            new InputStreamReader(input, StandardCharsets.UTF_8));
        return Integer.valueOf(br.readLine());
      } catch (NumberFormatException e) {
        out.println("Please input number.");
      } catch (IOException e) {
        throw new UncheckedIOException(e);
      }
    }
  }

  @Override
  public void viewProgress(int numOfTests, ReversiWinRateSimulateResult result) {
    if (numOfTests % intervalOfShowProgress != 0) {
      return;
    }
    StringBuilder progress = new StringBuilder();
    progress.append("Total:").append(result.getNumOfTest()).append(" ");
    progress.append(result.getTypeOfPlayerA()).append(":").append(result.getNumOfWinPlayerA())
        .append(", ");
    progress.append(result.getTypeOfPlayerB()).append(":").append(result.getNumOfWinPlayerB());
    out.println(progress.toString());
  }

  @Override
  public void viewResult(ReversiWinRateSimulateResult result) {
    StringBuilder progress = new StringBuilder();
    out.println("Result of Simulation");
    out.println(new StringBuilder().append("Total:").append(result.getNumOfTest()));
    out.println(
        new StringBuilder().append("Num of Wins ")
            .append(result.getTypeOfPlayerA()).append(":").append(result.getNumOfWinPlayerA())
            .append(", ")
            .append(result.getTypeOfPlayerB()).append(":").append(result.getNumOfWinPlayerB())
            .append(", ")
            .append("Draw:").append(result.getNumOfDraw()));
    out.println(
        new StringBuilder().append("Win rate ")
            .append(result.getTypeOfPlayerA()).append(":").append(result.getPlayerAWinRate())
            .append(", ")
            .append(result.getTypeOfPlayerB()).append(":").append(result.getPlayerBWinRate()));
  }
}
