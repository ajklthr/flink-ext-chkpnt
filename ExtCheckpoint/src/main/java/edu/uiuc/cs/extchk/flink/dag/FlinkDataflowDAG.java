package edu.uiuc.cs.extchk.flink.dag;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class FlinkDataflowDAG {

  private ObjectMapper objectMapper = new ObjectMapper();

  private String WORD_COUNT_PLAN_FILE_NAME = "wordcountplan.json";

  private JsonNode flinkPlan;

  private final int NUMOPERATORS = 5;

  public FlinkDataflowDAG() throws IOException {
    try (InputStream in = Thread.currentThread().getContextClassLoader()
        .getResourceAsStream(WORD_COUNT_PLAN_FILE_NAME)) {
      flinkPlan = objectMapper.readValue(in, JsonNode.class);
    }
  }

  public Set<String> getCausalUpstreamDependencies(String operatorId) {
    Set<String> orderedSet = new TreeSet<>();
    for (int i = Integer.valueOf(operatorId) - 1; i >= 0; i--) {
      orderedSet.add(String.valueOf(i));
    }
    return orderedSet;
  }

  //create
  public int getTotalOperatorInstances() {
    return NUMOPERATORS;
  }

  public String getSinkOperatorId() {
    return String.valueOf(NUMOPERATORS - 1);
  }
}
