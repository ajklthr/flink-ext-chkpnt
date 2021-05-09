package edu.uiuc.cs.extchk.flink.dag;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Set;

public class FlinkDataflowDAG {

  private ObjectMapper objectMapper = new ObjectMapper();

  private String WORD_COUNT_PLAN_FILE_NAME = "wordcountplan.json";

  private JsonNode flinkPlan;

  public FlinkDataflowDAG() throws IOException {
    try (InputStream in = Thread.currentThread().getContextClassLoader()
        .getResourceAsStream(WORD_COUNT_PLAN_FILE_NAME)) {
      flinkPlan = objectMapper.readValue(in, JsonNode.class);
    }
  }

  public Set<String> getCausalUpstreamDependencies(String operatorId) {
    return null;
  }

  //create
  public int getTotalOperatorInstances() {
    return 0;
  }

  public String getSinkOperatorId() {
    return null;
  }
}
