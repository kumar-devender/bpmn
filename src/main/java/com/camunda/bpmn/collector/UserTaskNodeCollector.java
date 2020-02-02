package com.camunda.bpmn.collector;

import com.camunda.bpmn.domain.Node;
import com.camunda.bpmn.domain.Sequence;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.camunda.bpm.model.bpmn.instance.UserTask;
import org.camunda.bpm.model.xml.instance.ModelElementInstance;
import org.camunda.bpm.model.xml.type.ModelElementType;

import java.util.Collection;
import java.util.List;

public class UserTaskNodeCollector extends AbstractNodeCollector {

    @Override
    public List<Node> getNodes(BpmnModelInstance modelInstance, List<Sequence> sequences) {
        ModelElementType userTaskType = modelInstance.getModel().getType(UserTask.class);
        Collection<ModelElementInstance> userTaskInstances = modelInstance.getModelElementsByType(userTaskType);
        return getNodes(userTaskInstances, sequences);
    }
}
