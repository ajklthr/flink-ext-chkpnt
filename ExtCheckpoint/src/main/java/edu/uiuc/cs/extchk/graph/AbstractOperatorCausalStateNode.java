package edu.uiuc.cs.extchk.graph;

import edu.uiuc.cs.extchk.VectorTimeStamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import lombok.Getter;

@Getter
public abstract class AbstractOperatorCausalStateNode {

  protected String operatorId;

  protected VectorTimeStamp vectorTimeStamp;

  protected AbstractOperatorCausalStateNode parent;

  protected AbstractOperatorCausalStateNode child;

  protected AbstractOperatorCausalStateNode happensBefore;

  protected List<AbstractOperatorCausalStateNode> happensAfterNodeList = new ArrayList<>();

  protected boolean isOrdered;

  boolean isBeforeThis(AbstractOperatorCausalStateNode abstractCausalStateNode) {
    return this.vectorTimeStamp
        .isEarlierProcessVClockTick(abstractCausalStateNode.getVectorTimeStamp());
  }

  boolean isAfterThis(AbstractOperatorCausalStateNode abstractOperatorCausalStateNode) {
    return this.vectorTimeStamp
        .isLaterProcessVClockTick(abstractOperatorCausalStateNode.getVectorTimeStamp());
  }


  public AbstractOperatorCausalStateNode orderStateNode(AbstractOperatorCausalStateNode stateNode) {
    if (isBeforeThis(stateNode)) {
      if (!isOrdered) {
        setParent(stateNode);
      } else {
        //Ignore
      }
    } else if (isAfterThis(stateNode)) {
      addDescendant(stateNode);
    } else {
      //Ignore
    }
    return this.parent;
  }

  private void setParent(AbstractOperatorCausalStateNode stateNode) {
    boolean isOrdered = vectorTimeStamp.isNextProcessVClockTick(stateNode.getVectorTimeStamp());
    if (this.parent != null) {
      this.parent.child = stateNode;
    }
    this.parent = stateNode;
    stateNode.child = this;
    this.isOrdered = isOrdered;
  }

  private void addDescendant(AbstractOperatorCausalStateNode stateNode) {

    if (this.getHappensAfterNodeList() != null) {
      Iterator<AbstractOperatorCausalStateNode> happensAfterIterator = this
          .getHappensAfterNodeList()
          .iterator();
      while (happensAfterIterator.hasNext()) {
        AbstractOperatorCausalStateNode happensAfter = happensAfterIterator.next();
        if (stateNode.vectorTimeStamp.isCausallyBefore(happensAfter.vectorTimeStamp)) {
          stateNode.happensAfterNodeList.add(happensAfter);
          happensAfter.happensBefore = stateNode;
        }
      }
    }

    if (this.getHappensBefore() != null && this.getHappensBefore().getVectorTimeStamp()
        .isCausallyBefore(stateNode.getVectorTimeStamp())) {
      if (this.child == null) {
        this.child = stateNode;
        stateNode.parent = this;
      } else {
        this.child.orderStateNode(stateNode);
      }
    }
  }

  public void notifyOperatorStateNode(AbstractOperatorCausalStateNode stateNode) {
    if (this.child != null &&
        this.child.getVectorTimeStamp().isCausallyBefore(stateNode.getVectorTimeStamp())) {
      this.child.notifyOperatorStateNode(stateNode);
    } else if (!this.getVectorTimeStamp().isConcurrent(stateNode.getVectorTimeStamp())) {
      this.getHappensAfterNodeList().add(stateNode);
      stateNode.happensBefore = this;
    }
  }

  public abstract void calculateConsistentCut(ConsistentCutContext consistentCutContext);

  public void findLatestHappensBeforeForOperator(String operatorId,
      ConsistentCutContext consistentCutContext) {
    if (this.happensBefore != null && this.happensBefore.getOperatorId().equals(operatorId)) {
      consistentCutContext.setLatestHappensBeforeForOperator(this.getHappensBefore());
    }
    if (this.parent == null) {
      consistentCutContext.setLatestHappensBeforeForOperator(
          consistentCutContext.getInitialStateForOperator(operatorId));
    } else {
      this.findLatestHappensBeforeForOperator(operatorId, consistentCutContext);
    }
  }

}
