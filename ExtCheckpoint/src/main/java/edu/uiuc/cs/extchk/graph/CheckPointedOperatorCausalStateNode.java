package edu.uiuc.cs.extchk.graph;

import edu.uiuc.cs.extchk.VectorTimeStamp;

public class CheckPointedOperatorCausalStateNode extends AbstractOperatorCausalStateNode {

  boolean isInitState = true;

  public CheckPointedOperatorCausalStateNode(boolean isInitState, String operatorId,
      VectorTimeStamp vectorTimeStamp) {
    this.isInitState = isInitState;
    this.operatorId = operatorId;
    this.vectorTimeStamp = vectorTimeStamp;
  }


  public static CheckPointedOperatorCausalStateNode createInitStateNode(String operatorId,
      VectorTimeStamp vectorTimeStamp) {
    return new CheckPointedOperatorCausalStateNode(true, operatorId, vectorTimeStamp);
  }

  @Override
  public void processCompaction(GraphCompactionContext graphCompactionContext) {
    
  }
}
