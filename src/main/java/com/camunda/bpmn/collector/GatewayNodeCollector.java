package com.camunda.bpmn.collector;

import com.camunda.bpmn.domain.Node;
import com.camunda.bpmn.domain.Sequence;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.camunda.bpm.model.bpmn.instance.Gateway;
import org.camunda.bpm.model.xml.instance.ModelElementInstance;
import org.camunda.bpm.model.xml.type.ModelElementType;

import java.util.Collection;
import java.util.List;

public class GatewayNodeCollector extends AbstractNodeCollector {

    @Override
    public List<Node> getNodes(BpmnModelInstance modelInstance, List<Sequence> sequences) {
        ModelElementType gatewayTaskType = modelInstance.getModel().getType(Gateway.class);
        Collection<ModelElementInstance> gateways = modelInstance.getModelElementsByType(gatewayTaskType);
        List<Node> nodes = getNodes(gateways, sequences);
        nodes.forEach(node -> node.setGatewayNode(true));
        return nodes;
    }
}
