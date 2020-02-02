package com.camunda.bpmn.collector;

import com.camunda.bpmn.domain.Node;
import com.camunda.bpmn.domain.Sequence;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;

import java.util.List;

public interface NodeCollector {
    List<Node> getNodes(BpmnModelInstance modelInstance, List<Sequence> sequences);
}
