package edu.uiuc.cs.extchk.graph;

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
    void addNode() throws IOException {
        CausalStateGraph graph = new CausalStateGraph();
        OperatorCausalStateNode node = new OperatorCausalStateNode(null,
                new HashMap<>(),
                new HashMap<>(),
                "0");
        graph.addNode(node);
        assert(true);
    }

    @Test
    void checkpoint() {
    }
}