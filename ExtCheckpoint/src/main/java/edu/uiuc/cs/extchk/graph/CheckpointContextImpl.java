package edu.uiuc.cs.extchk.graph;

import edu.uiuc.cs.extchk.flink.dag.FlinkDataflowDAG;
import edu.uiuc.cs.extchk.flink.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 *
 */
public class CheckpointContextImpl implements ConsistentCutContext {

  int checkpointId;
  private FlinkDataflowDAG flinkDataflowDAG;
  private Map<String, Integer> operatorTotalSentMessage = new HashMap<>();
  private Map<String, Integer> operatorTotalRecvMessage = new HashMap<>();
  protected AbstractOperatorCausalStateNode latestHappensBefore;
  private boolean cancel;

  public CheckpointContextImpl(int checkpointId, FlinkDataflowDAG flinkDataflowDAG) {
    this.checkpointId = checkpointId;
    this.flinkDataflowDAG = flinkDataflowDAG;
    this.cancel = false;
    this.latestHappensBefore = null;
  }

  @Override
  public boolean isCancelled() {
    return this.cancel;
  }

  @Override
  public void cancel() {
    this.cancel = true;
  }

  @Override
  public void setFlinkDataFlowDAG(FlinkDataflowDAG flinkDataflowDAG) {
    this.flinkDataflowDAG = flinkDataflowDAG;
  }

  @Override
  public FlinkDataflowDAG getFlinkDataFlowDAG() {
    return this.flinkDataflowDAG;
  }

  @Override
  public void putOperatorStateNode(String operatorId, AbstractOperatorCausalStateNode node) {
    // Update total recv and send counts for operator
    OperatorCausalStateNode nodeWithChannelState = (OperatorCausalStateNode) node;
    operatorTotalRecvMessage.put(operatorId, nodeWithChannelState.getInChannelStates().get(operatorId));
    operatorTotalSentMessage.put(operatorId, nodeWithChannelState.getOutChannelStates().get(operatorId));

  }

  @Override
  public void setLatestHappensBeforeForOperator(
      AbstractOperatorCausalStateNode abstractOperatorCausalStateNode) {
    this.latestHappensBefore = abstractOperatorCausalStateNode;
  }

  @Override
  public AbstractOperatorCausalStateNode getLatestHappensBeforeForOperator() {
    return this.latestHappensBefore;
  }

  @Override
  public AbstractOperatorCausalStateNode getInitialStateForOperator(String operatorId) {
    AbstractOperatorCausalStateNode pointer = this.latestHappensBefore;
    while (pointer != null && pointer.getOperatorId() != operatorId) {
      pointer = pointer.happensBefore;
    }
    while (pointer.child != null) {
      pointer = pointer.child;
    }
    return pointer;
  }

  public AbstractOperatorCausalStateNode getEarliestNodeInCheckPoint(String operatorId){
    AbstractOperatorCausalStateNode pointer = this.latestHappensBefore;
    while (pointer != null && pointer.getOperatorId() != operatorId) {
      pointer = pointer.happensBefore;
    }

    OperatorCausalStateNode nodeWithChannelStates = (OperatorCausalStateNode) pointer;
    int jumpCount = nodeWithChannelStates.getInChannelStates().get(Integer.valueOf(operatorId)) -
            nodeWithChannelStates.getOutChannelStates().get(Integer.valueOf(operatorId));
    for (int i = 0; i < jumpCount; i--) {
      nodeWithChannelStates = (OperatorCausalStateNode) nodeWithChannelStates.child;
    }

    return nodeWithChannelStates;


    // Get the operator state for id, Move up by the difference in message count
    // Return the node;
  }
}
