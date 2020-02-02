package com.camunda.bpmn.collector;

import com.camunda.bpmn.domain.Node;
import com.camunda.bpmn.domain.Sequence;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.camunda.bpm.model.bpmn.instance.EndEvent;
import org.camunda.bpm.model.xml.instance.ModelElementInstance;
import org.camunda.bpm.model.xml.type.ModelElementType;

import java.util.Collection;
import java.util.List;

public class EndEventNodeCollector extends AbstractNodeCollector {

    @Override
    public List<Node> getNodes(BpmnModelInstance modelInstance, List<Sequence> sequences) {
        ModelElementType endEventType = modelInstance.getModel().getType(EndEvent.class);
        Collection<ModelElementInstance> endEvents = modelInstance.getModelElementsByType(endEventType);
        return getNodes(endEvents, sequences);
    }
}
