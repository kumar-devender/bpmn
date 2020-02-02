package com.camunda.bpmn.collector;

import com.camunda.bpmn.domain.Node;
import com.camunda.bpmn.domain.Sequence;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.camunda.bpm.model.bpmn.instance.BusinessRuleTask;
import org.camunda.bpm.model.xml.instance.ModelElementInstance;
import org.camunda.bpm.model.xml.type.ModelElementType;

import java.util.Collection;
import java.util.List;

public class BusinessTaskRuleNodeCollector extends AbstractNodeCollector {

    @Override
    public List<Node> getNodes(BpmnModelInstance modelInstance, List<Sequence> sequences) {
        ModelElementType businessRuleTaskType = modelInstance.getModel().getType(BusinessRuleTask.class);
        Collection<ModelElementInstance> businessRuleTasks = modelInstance.getModelElementsByType(businessRuleTaskType);
        return getNodes(businessRuleTasks, sequences);
    }
}
