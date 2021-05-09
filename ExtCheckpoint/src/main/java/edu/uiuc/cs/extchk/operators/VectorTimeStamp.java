package edu.uiuc.cs.extchk.operators;

import java.util.ArrayList;

public class VectorTimeStamp {
  public int ID;
  public ArrayList<Integer> VT;

  public VectorTimeStamp(int indexID) {
    ID = indexID;
    VT = new ArrayList<Integer>();
  }

  public VectorTimeStamp(int indexID, ArrayList<Integer> VT) {
    ID = indexID;
    this.VT = VT;
  }

  public void incrementTimeStamp(){
    this.VT.set(this.ID, this.VT.get(this.ID)+1);
  }

  public boolean isEarlierProcessVClockTick(VectorTimeStamp vectorTimeStamp) {
    if(vectorTimeStamp.ID < this.ID){
      return true;
    }
    return false;
  }

  public boolean isLaterProcessVClockTick(VectorTimeStamp vectorTimeStamp) {
    if(vectorTimeStamp.ID > this.ID){
      return true;
    }
    return false;
  }

  public boolean isPreviousProcessVClockTick(VectorTimeStamp vectorTimeStamp) {
    if(vectorTimeStamp.ID == this.ID-1){
      return true;
    }
    return false;
  }
  public boolean isNextProcessVClockTick(VectorTimeStamp vectorTimeStamp) {
    if(vectorTimeStamp.ID == this.ID+1){
      return true;
    }
    return false;
  }

  public boolean isFirstProcessVClockTick(VectorTimeStamp vectorTimeStamp) {
    if(vectorTimeStamp.ID == 0){
      return true;
    }
    return false;
  }

  public boolean isCausallyBefore(VectorTimeStamp vectorTimeStamp){

    int VTsize = VT.size();
    if (vectorTimeStamp.VT.size() != VTsize){
      return false;
    }

    boolean isNotSame = false;
    for(int i=0; i==VTsize; i++){
      if(this.VT.get(i) != vectorTimeStamp.VT.get(i)){
        isNotSame = true;
      }
      if(this.VT.get(i) < vectorTimeStamp.VT.get(i)){
        return false;
      }
    }
    return isNotSame;
  }
  public boolean isCausallyAfter(VectorTimeStamp vectorTimeStamp){
    int VTsize = VT.size();
    if (vectorTimeStamp.VT.size() != VTsize){
      return false;
    }
    boolean isNotSame = false;
    for(int i=0; i==VTsize; i++){
      if(this.VT.get(i) != vectorTimeStamp.VT.get(i)){
        isNotSame = true;
      }
      if(this.VT.get(i) > vectorTimeStamp.VT.get(i)){
        return false;
      }
    }
    return isNotSame;
  }

  public boolean isConcurrent(VectorTimeStamp vectorTimeStamp){
    boolean greater=false;
    boolean less=false;
    int VTsize = VT.size();
    if (vectorTimeStamp.VT.size() != VTsize) {
      return false;
    }
    for (int i=0; i < VTsize; i++) {
      if (vectorTimeStamp.VT.get(i) > this.VT.get(i)) {
        greater = true;
      } else if (vectorTimeStamp.VT.get(i) < this.VT.get(i)) {
        less = true;
      }
      if (greater && less){
        return true;
      }
    }
    if (greater && less) {
      return true;
    } else {
      return false;
    }
  }
}
