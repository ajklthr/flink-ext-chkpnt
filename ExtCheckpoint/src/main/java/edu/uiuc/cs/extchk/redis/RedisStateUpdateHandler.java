package edu.uiuc.cs.extchk.redis;

import edu.uiuc.cs.extchk.VectorTimeStamp;
import edu.uiuc.cs.extchk.graph.CausalStateGraph;

public class RedisStateUpdateHandler {

  private CausalStateGraph causalStateGraph;

  public RedisStateUpdateHandler(CausalStateGraph causalStateGraph) {
    this.causalStateGraph = causalStateGraph;
  }

  public void handleUpdate(String operatorId, VectorTimeStamp vectorTimeStamp, byte[] state){

  }

}
 