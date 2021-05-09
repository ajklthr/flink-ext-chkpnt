package edu.uiuc.cs.extchk.graph;

import edu.uiuc.cs.extchk.flink.dag.FlinkDataflowDAG;
import edu.uiuc.cs.extchk.flink.*;
import edu.uiuc.cs.extchk.operators.VectorTimeStamp;

import java.lang.reflect.Array;
import java.util.*;

/**
 *
 */
public class CheckpointContextImpl implements ConsistentCutContext {

  int checkpointId;
  private FlinkDataflowDAG flinkDataflowDAG;
  private Map<String, Integer> operatorTotalSentMessage = new HashMap<>();
  private Map<String, Integer> operatorTotalRecvMessage = new HashMap<>();
  protected AbstractOperatorCausalStateNode latestHappensBefore;
  protected Set<AbstractOperatorCausalStateNode> nodesInCut;
  private boolean cancel;

  public CheckpointContextImpl(int checkpointId, FlinkDataflowDAG flinkDataflowDAG) {
    this.checkpointId = checkpointId;
    this.flinkDataflowDAG = flinkDataflowDAG;
    this.cancel = false;
    this.latestHappensBefore = null;
    nodesInCut = new HashSet<>();
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

    int newRecv = nodeWithChannelState.getInChannelStates().get(operatorId);
    int newSent = nodeWithChannelState.getOutChannelStates().get(operatorId);

    if (operatorTotalRecvMessage.get(operatorId) == null) {
      operatorTotalRecvMessage.put(operatorId, newRecv);
    } else {
      operatorTotalRecvMessage.put(operatorId, operatorTotalRecvMessage.get(operatorId) + newRecv);
    }

    if (operatorTotalSentMessage.get(operatorId) == null) {
      operatorTotalSentMessage.put(operatorId, newSent);
    } else {
      operatorTotalSentMessage.put(operatorId, operatorTotalSentMessage.get(operatorId) + newSent);
    }

    nodesInCut.add(node);

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

    OperatorCausalStateNode toReturn = new OperatorCausalStateNode();
    ArrayList<Integer> vt = new ArrayList<>();
    int vtsize = this.flinkDataflowDAG.getTotalOperatorInstances();
    for (int i = 0; i < vtsize; i++) {
      vt.add(0);
    }
    VectorTimeStamp vectorTimestamp = new VectorTimeStamp(Integer.valueOf(operatorId), vt);
    toReturn.vectorTimeStamp = vectorTimestamp;


    return toReturn;
  }

  public AbstractOperatorCausalStateNode getEarliestNodeInCheckPoint(String operatorId){
    AbstractOperatorCausalStateNode match = null ;
    for (AbstractOperatorCausalStateNode node: nodesInCut) {

      if (node.getOperatorId().equals(operatorId)) {
        match = node;
        break;
      }
    }

//    OperatorCausalStateNode nodeWithChannelStates = (OperatorCausalStateNode) match;
//    int jumpCount = nodeWithChannelStates.getInChannelStates().get(Integer.valueOf(operatorId)) -
//            nodeWithChannelStates.getOutChannelStates().get(Integer.valueOf(operatorId));
//    for (int i = 0; i < jumpCount; i--) {
//      nodeWithChannelStates = (OperatorCausalStateNode) nodeWithChannelStates.getParent();
//    }

    return match;


    // Get the operator state for id, Move up by the difference in message count
    // Return the node;
  }
}
