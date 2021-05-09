package edu.uiuc.cs.extchk.graph;

import edu.uiuc.cs.extchk.flink.dag.FlinkDataflowDAG;

//TODO
public class GraphCompactionContext {

  protected FlinkDataflowDAG flinkDataflowDAG;
  private boolean terminated;

  public GraphCompactionContext(FlinkDataflowDAG flinkDataflowDAG) {
    this.flinkDataflowDAG = flinkDataflowDAG;
    this.terminated = false;
  }

  public void terminate() {
    this.terminated = true;
  }

}
