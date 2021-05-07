package edu.uiuc.cs.extchk.graph;

import java.util.Map;
import lombok.Getter;

@Getter
public class OperatorCausalStateNode extends AbstractOperatorCausalStateNode {

  private byte[] state;

  private Map<Integer, Integer> inChannelStates;

  private Map<Integer, Integer> outChannelStates;

  @Override
  public void processCompaction(GraphCompactionContext graphCompactionContext) {
    if (!this.isOrdered) {
      graphCompactionContext.terminate();
      return;
    }

  }
}
