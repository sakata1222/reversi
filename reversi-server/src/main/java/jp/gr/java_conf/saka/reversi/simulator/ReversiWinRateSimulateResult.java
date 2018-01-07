package jp.gr.java_conf.saka.reversi.simulator;

import java.util.concurrent.atomic.AtomicInteger;

public class ReversiWinRateSimulateResult {

  private AtomicInteger numOfTest;
  private String typeOfPlayerA;
  private AtomicInteger numOfWinPlayerA;
  private String typeOfPlayerB;
  private AtomicInteger numOfWinPlayerB;
  private AtomicInteger numOfDraw;

  ReversiWinRateSimulateResult(String typeOfPlayerA, String typeOfPlayerB) {
    this.numOfTest = new AtomicInteger(0);
    this.typeOfPlayerA = typeOfPlayerA;
    this.numOfWinPlayerA = new AtomicInteger(0);
    this.typeOfPlayerB = typeOfPlayerB;
    this.numOfWinPlayerB = new AtomicInteger(0);
    this.numOfDraw = new AtomicInteger(0);
  }

  void playerAWon() {
    numOfTest.incrementAndGet();
    numOfWinPlayerA.incrementAndGet();
  }

  void playerBWon() {
    numOfTest.incrementAndGet();
    numOfWinPlayerB.incrementAndGet();
  }

  void draw() {
    numOfTest.incrementAndGet();
    numOfDraw.incrementAndGet();
  }

  public int getNumOfTest() {
    return numOfTest.intValue();
  }

  public String getTypeOfPlayerA() {
    return typeOfPlayerA;
  }

  public int getNumOfWinPlayerA() {
    return numOfWinPlayerA.intValue();
  }

  public String getTypeOfPlayerB() {
    return typeOfPlayerB;
  }

  public int getNumOfWinPlayerB() {
    return numOfWinPlayerB.intValue();
  }

  public int getNumOfDraw() {
    return numOfDraw.intValue();
  }

  public double getPlayerAWinRate() {
    return numOfWinPlayerA.doubleValue() / numOfTest.doubleValue();
  }

  public double getPlayerBWinRate() {
    return numOfWinPlayerB.doubleValue() / numOfTest.doubleValue();
  }
}
