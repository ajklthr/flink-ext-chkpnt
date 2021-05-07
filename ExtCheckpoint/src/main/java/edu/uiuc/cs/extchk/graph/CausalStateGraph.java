package edu.uiuc.cs.extchk.graph;

import edu.uiuc.cs.extchk.flink.dag.FlinkDataflowDAG;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

public class CausalStateGraph {

  private ConcurrentHashMap<String, AbstractOperatorCausalStateNode> nodesIndexedByOperatorId = new ConcurrentHashMap<>();


  public AbstractOperatorCausalStateNode getEarliestStateNodeForOperatorId(String operatorId) {
    return nodesIndexedByOperatorId.get(operatorId);
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

  public void compact() throws IOException {
    //TODO Multi-threading must acquire locks on nodes
    FlinkDataflowDAG flinkDataflowDAG = new FlinkDataflowDAG();
    String sinkOperatorId = flinkDataflowDAG.getSinkOperatorId();
    GraphCompactionContext graphCompactionContext = new GraphCompactionContext(flinkDataflowDAG);


  }

  public void checkpoint() {

  }

}
