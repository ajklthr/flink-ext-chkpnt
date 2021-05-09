package edu.uiuc.cs.extchk.graph;

import edu.uiuc.cs.extchk.operators.VectorTimeStamp;
import java.util.Arrays;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class CausalStateGraphTest {

  @Test
  void getEarliestStateNodeForOperatorId() {

  }

  @Test
  void getNthStateForOperatorId() {
  }

  @Test
  void addInitialNode() throws IOException {
    CausalStateGraph graph = new CausalStateGraph();
    OperatorCausalStateNode node = new OperatorCausalStateNode(null,
        new HashMap<>(),
        new HashMap<>(),
        "0", new VectorTimeStamp(0, Arrays.asList(new Integer[]{0, 0, 0, 0, 0})), true);
    graph.addNode(node);
    assertNotNull(graph.getEarliestStateNodeForOperatorId("0"));

  }

  @Test
  void addAnotherNode() throws IOException {
    CausalStateGraph graph = new CausalStateGraph();
    OperatorCausalStateNode node = new OperatorCausalStateNode(null,
        new HashMap<>(),
        new HashMap<>(),
        "0", new VectorTimeStamp(0, Arrays.asList(new Integer[]{0, 0, 0, 0, 0})), true);
    graph.addNode(node);

    OperatorCausalStateNode nextNode = new OperatorCausalStateNode(null,
        new HashMap<>(),
        new HashMap<>(),
        "0", new VectorTimeStamp(0, Arrays.asList(new Integer[]{1, 0, 0, 0, 0})), false);
    graph.addNode(nextNode);


    assertNotNull(graph.getEarliestStateNodeForOperatorId("0"));
    assertNotNull(graph.getEarliestStateNodeForOperatorId("0").getVectorTimeStamp().equals( Arrays.asList(new Integer[]{0, 0, 0, 0, 0})));
    assertNotNull(graph.getEarliestStateNodeForOperatorId("0").child);
    assertNotNull(graph.getEarliestStateNodeForOperatorId("0").child.getVectorTimeStamp().equals( Arrays.asList(new Integer[]{1, 0, 0, 0, 0})));
    assertTrue(graph.getEarliestStateNodeForOperatorId("0").child.isOrdered);
  }


  @Test
  void addAnOutOfOrderNode() throws IOException {
    CausalStateGraph graph = new CausalStateGraph();
    OperatorCausalStateNode node = new OperatorCausalStateNode(null,
        new HashMap<>(),
        new HashMap<>(),
        "0", new VectorTimeStamp(0, Arrays.asList(new Integer[]{0, 0, 0, 0, 0})), true);
    graph.addNode(node);

    OperatorCausalStateNode nextNode = new OperatorCausalStateNode(null,
        new HashMap<>(),
        new HashMap<>(),
        "0", new VectorTimeStamp(0, Arrays.asList(new Integer[]{2, 0, 0, 0, 0})), false);
    graph.addNode(nextNode);

    OperatorCausalStateNode thirdNode = new OperatorCausalStateNode(null,
        new HashMap<>(),
        new HashMap<>(),
        "0", new VectorTimeStamp(0, Arrays.asList(new Integer[]{1, 0, 0, 0, 0})), false);
    graph.addNode(nextNode);


    assertNotNull(graph.getEarliestStateNodeForOperatorId("0"));
    assertNotNull(graph.getEarliestStateNodeForOperatorId("0").getVectorTimeStamp().equals( Arrays.asList(new Integer[]{0, 0, 0, 0, 0})));
    assertNotNull(graph.getEarliestStateNodeForOperatorId("0").child);
    assertNotNull(graph.getEarliestStateNodeForOperatorId("0").child.getVectorTimeStamp().equals( Arrays.asList(new Integer[]{1, 0, 0, 0, 0})));
    assertTrue(graph.getEarliestStateNodeForOperatorId("0").child.isOrdered);
  }

  @Test
  void checkpoint() {
  }
}