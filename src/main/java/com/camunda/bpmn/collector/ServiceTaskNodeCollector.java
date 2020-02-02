package com.camunda.bpmn.collector;

import com.camunda.bpmn.domain.Node;
import com.camunda.bpmn.domain.Sequence;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.camunda.bpm.model.bpmn.instance.ServiceTask;
import org.camunda.bpm.model.xml.instance.ModelElementInstance;
import org.camunda.bpm.model.xml.type.ModelElementType;

import java.util.Collection;
import java.util.List;

public class ServiceTaskNodeCollector extends AbstractNodeCollector {

    @Override
    public List<Node> getNodes(BpmnModelInstance modelInstance, List<Sequence> sequences) {
        ModelElementType serviceTaskType = modelInstance.getModel().getType(ServiceTask.class);
        Collection<ModelElementInstance> serviceTaskInstances = modelInstance.getModelElementsByType(serviceTaskType);
        return getNodes(serviceTaskInstances, sequences);
    }
}
