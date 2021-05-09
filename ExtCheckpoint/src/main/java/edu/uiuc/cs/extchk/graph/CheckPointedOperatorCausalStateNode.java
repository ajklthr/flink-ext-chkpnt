package edu.uiuc.cs.extchk.graph;

import edu.uiuc.cs.extchk.flink.dag.FlinkDataflowDAG;
import edu.uiuc.cs.extchk.operators.VectorTimeStamp;

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
  public void calculateConsistentCut(ConsistentCutContext consistentCutContext){};

  public void processCompaction(GraphCompactionContext graphCompactionContext) {
    graphCompactionContext.terminate();

  }
}
