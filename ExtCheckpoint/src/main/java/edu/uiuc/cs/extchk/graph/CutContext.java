package edu.uiuc.cs.extchk.graph;

//TODO
public interface CutContext {

  void invalidate();

  void updateSentMessageCount(String destinationOperatorId, int count);

  void updateRecvMessageCount(String operatorId, int count);
}
