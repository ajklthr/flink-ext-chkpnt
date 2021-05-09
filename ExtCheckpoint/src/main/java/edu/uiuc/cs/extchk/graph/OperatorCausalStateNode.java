package edu.uiuc.cs.extchk.graph;

import edu.uiuc.cs.extchk.operators.VectorTimeStamp;
import java.util.List;
import java.util.Map;
import java.util.Set;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class OperatorCausalStateNode extends AbstractOperatorCausalStateNode {

  private byte[] state;

  private Map<Integer, Integer> inChannelStates;

  private Map<Integer, Integer> outChannelStates;


  public OperatorCausalStateNode(byte[] state, Map<Integer, Integer> inChannelStates,
                                 Map<Integer, Integer> outChannelStates, String operatorID, VectorTimeStamp vectorTimeStamp, boolean isOrdered) {
    this.state = state;
    this.inChannelStates = inChannelStates;
    this.outChannelStates = outChannelStates;
    this.operatorId = operatorID;
    this.isOrdered = isOrdered;

  }


  @Override
  public void calculateConsistentCut(ConsistentCutContext consistentCutContext) {
    if (!this.isOrdered) {
      consistentCutContext.cancel();
    }
    consistentCutContext.putOperatorStateNode(this.getOperatorId(), this);
    Set<String> operators = consistentCutContext.getFlinkDataFlowDAG()
        .getCausalUpstreamDependencies(this.getOperatorId());
    if (this.getHappensBefore() != null) {
      operators.remove(this.happensBefore.operatorId);
      for (String operatorId : operators) {
        findLatestHappensBeforeForOperator(operatorId, consistentCutContext);
        consistentCutContext.putOperatorStateNode(operatorId,
            consistentCutContext.getLatestHappensBeforeForOperator());
      }
    }
    if (this.happensBefore != null) {
      happensBefore.calculateConsistentCut(consistentCutContext);
    }
  }
}
