package edu.uiuc.cs.extchk;

public class VectorTimeStamp {


  public boolean isEarlierProcessVClockTick(VectorTimeStamp vectorTimeStamp) {
    return false;
  }

  public boolean isLaterProcessVClockTick(VectorTimeStamp vectorTimeStamp) {
    return false;
  }

  public boolean isPreviousProcessVClockTick(VectorTimeStamp vectorTimeStamp) {
    return false;
  }
  public boolean isNextProcessVClockTick(VectorTimeStamp vectorTimeStamp) {
    return false;
  }

  public boolean isFirstProcessVClockTick(VectorTimeStamp vectorTimeStamp) {
    return false;
  }

  public boolean isCausallyBefore(VectorTimeStamp vectorTimeStamp){
    return false;
  }
  public boolean isCausallyAfter(VectorTimeStamp vectorTimeStamp){
    return false;
  }

  public boolean isConcurrent(VectorTimeStamp vectorTimeStamp){
    return false;
  }


}
