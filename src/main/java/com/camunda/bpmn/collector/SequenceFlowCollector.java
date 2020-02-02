package com.camunda.bpmn.collector;

import com.camunda.bpmn.domain.Sequence;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.camunda.bpm.model.bpmn.instance.SequenceFlow;
import org.camunda.bpm.model.xml.instance.DomElement;
import org.camunda.bpm.model.xml.instance.ModelElementInstance;
import org.camunda.bpm.model.xml.type.ModelElementType;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class SequenceFlowCollector {

    public List<Sequence> getSequences(BpmnModelInstance bpmnModelInstance) {
        ModelElementType sequenceFlowType = bpmnModelInstance.getModel().getType(SequenceFlow.class);
        Collection<ModelElementInstance> sequenceInstances = bpmnModelInstance.getModelElementsByType(sequenceFlowType);
        return sequenceInstances.stream()
                .map(ModelElementInstance::getDomElement)
                .map(DomElement::getModelElementInstance)
                .map(ModelElementInstance::getDomElement)
                .map(this::buildSequence)
                .collect(Collectors.toList());
    }

    private Sequence buildSequence(DomElement element) {
        String id = element.getAttribute("id");
        String sourceRef = element.getAttribute("sourceRef");
        String targetRef = element.getAttribute("targetRef");
        return new Sequence(id, sourceRef, targetRef);
    }
}
