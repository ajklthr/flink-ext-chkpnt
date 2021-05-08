package edu.uiuc.cs.extchk.graph;

import edu.uiuc.cs.extchk.flink.dag.FlinkDataflowDAG;
import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class CheckpointContextImpl implements ConsistentCutContext {

  int checkpointId;
  private FlinkDataflowDAG flinkDataflowDAG;
  private Map<String, Integer> operatorTotalSentMessage = new HashMap<>();
  private Map<String, Integer> operatorTotalRecvMessage = new HashMap<>();
  private boolean cancel;

  public CheckpointContextImpl(int checkpointId, FlinkDataflowDAG flinkDataflowDAG) {
    this.checkpointId = checkpointId;
    this.flinkDataflowDAG = flinkDataflowDAG;
    this.cancel = false;
  }

  @Override
  public void isCancelled() {

  }

  @Override
  public void cancel() {

  }

  @Override
  public void setFlinkDataFlowDAG(FlinkDataflowDAG flinkDataflowDAG) {

  }

  @Override
  public FlinkDataflowDAG getFlinkDataFlowDAG() {
    return null;
  }

  @Override
  public void putOperatorStateNode(String operatorId, AbstractOperatorCausalStateNode node) {
    // Update total recv and send counts for operator
  }

  @Override
  public void setLatestHappensBeforeForOperator(
      AbstractOperatorCausalStateNode abstractOperatorCausalStateNode) {

  }

  @Override
  public AbstractOperatorCausalStateNode getLatestHappensBeforeForOperator() {
    return null;
  }

  @Override
  public AbstractOperatorCausalStateNode getInitialStateForOperator(String operatorId) {
    return null;
  }

  public AbstractOperatorCausalStateNode getEarliestNodeInCheckPoint(String operatorId){
    return null;
    // Get the operator state for id, Move up by the difference in message count
    // Return the node;
  }
}
