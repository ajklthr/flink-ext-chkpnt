package edu.uiuc.cs.extchk.graph;

import edu.uiuc.cs.extchk.flink.dag.FlinkDataflowDAG;

/**
 * Context used to calculate a consistent cut.
 */
public interface ConsistentCutContext {

  void isCancelled();

  void cancel();

  void setFlinkDataFlowDAG(FlinkDataflowDAG flinkDataflowDAG);

  FlinkDataflowDAG getFlinkDataFlowDAG();

  void putOperatorStateNode(String operatorId, AbstractOperatorCausalStateNode node);

  void setLatestHappensBeforeForOperator(
      AbstractOperatorCausalStateNode abstractOperatorCausalStateNode);

  AbstractOperatorCausalStateNode getLatestHappensBeforeForOperator();

  AbstractOperatorCausalStateNode getInitialStateForOperator(String operatorId);
}
