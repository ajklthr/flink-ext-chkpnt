package edu.uiuc.cs.extchk.graph;

import edu.uiuc.cs.extchk.flink.dag.FlinkDataflowDAG;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class CausalStateGraph {

  private ConcurrentHashMap<String, AbstractOperatorCausalStateNode> nodesIndexedByOperatorId = new ConcurrentHashMap<>();


  public AbstractOperatorCausalStateNode getEarliestStateNodeForOperatorId(String operatorId) {
    return nodesIndexedByOperatorId.get(operatorId);
  }

  public AbstractOperatorCausalStateNode getNthStateForOperatorId(String operatorId, int n) {
    AbstractOperatorCausalStateNode pointer = getEarliestStateNodeForOperatorId(operatorId);
    for (int i = 0; i < n; i++) {
      pointer = pointer.getChild();
    }
    return pointer;
  }

  private Set<String> getAllOperators() {
    return new HashSet<>(new HashSet<>(Arrays.asList("0,","1","2","3","4")));
  }

  public void addNode(OperatorCausalStateNode operatorCausalStateNode)
      throws IOException {
    AbstractOperatorCausalStateNode earliestOperatorCausalStateNode = getEarliestStateNodeForOperatorId(
        operatorCausalStateNode.getOperatorId());
    if (earliestOperatorCausalStateNode == null) {
      nodesIndexedByOperatorId
          .put(operatorCausalStateNode.getOperatorId(), operatorCausalStateNode);
    }
    earliestOperatorCausalStateNode.orderStateNode(operatorCausalStateNode);

    FlinkDataflowDAG flinkDataflowDAG = new FlinkDataflowDAG();
    for (String causalOperatorId : flinkDataflowDAG
        .getCausalUpstreamDependencies(operatorCausalStateNode.getOperatorId())) {
      getEarliestStateNodeForOperatorId(causalOperatorId)
          .notifyOperatorStateNode(operatorCausalStateNode);
    }

  }

  /**
   * Calculate a maximal consistent cut including nth sink state
   *
   * @throws IOException
   */
  private void calculateConsistentCut(ConsistentCutContext consistentCutContext) throws IOException {
    //TODO Multi-threading must acquire locks on nodes
    AbstractOperatorCausalStateNode abstractOperatorCausalStateNode = getNthStateForOperatorId(
        consistentCutContext.getFlinkDataFlowDAG().getSinkOperatorId(), 2);
    if (abstractOperatorCausalStateNode == null) {
      consistentCutContext.cancel();
      return;
    }
    abstractOperatorCausalStateNode.calculateConsistentCut(consistentCutContext);
  }

  public void checkpoint() throws IOException {
    CheckpointContextImpl checkpointContext = new CheckpointContextImpl(1,
        new FlinkDataflowDAG()); //TODO set checkpoint Id
    calculateConsistentCut(checkpointContext);
    //Snapshot checkpoint + Channel state calculation - Ignore for now
    //Compact graph
    //If multi-threading appropriate locking needed
    for (String operatorId : getAllOperators()) {
      AbstractOperatorCausalStateNode abstractOperatorCausalStateNode = getEarliestStateNodeForOperatorId(operatorId);
      abstractOperatorCausalStateNode.parent = null;
      this.nodesIndexedByOperatorId.put(operatorId, abstractOperatorCausalStateNode);
    }
  }


}
