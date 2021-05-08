package edu.uiuc.cs.extchk.graph;

import edu.uiuc.cs.extchk.VectorTimeStamp;

public class CompactedOperatorCausalStateNode extends AbstractOperatorCausalStateNode {

  boolean isInitState = true;

  public CompactedOperatorCausalStateNode(boolean isInitState, String operatorId,
      VectorTimeStamp vectorTimeStamp) {
    this.isInitState = isInitState;
    this.operatorId = operatorId;
    this.vectorTimeStamp = vectorTimeStamp;
  }


  public static CompactedOperatorCausalStateNode createInitStateNode(String operatorId,
      VectorTimeStamp vectorTimeStamp) {
    return new CompactedOperatorCausalStateNode(true, operatorId, vectorTimeStamp);
  }

  @Override
  public void calculateConsistentCut(ConsistentCutContext consistentCutContext) {

  }
}
