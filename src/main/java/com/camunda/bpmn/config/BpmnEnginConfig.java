package com.camunda.bpmn.config;

import com.camunda.bpmn.collector.*;
import com.camunda.bpmn.engin.BpmnPathEngine;

import java.util.Arrays;
import java.util.List;

public class BpmnEnginConfig {
    public static BpmnPathEngine getBpmnPathEngin() {
        return new BpmnPathEngine(getSequenceFlowCollector(), getNodeFlowCollector());
    }

    private static SequenceFlowCollector getSequenceFlowCollector() {
        return new SequenceFlowCollector();
    }

    private static List<NodeCollector> getNodeFlowCollector() {
        return Arrays.asList(new CallActivityNodeCollector(),
                new ServiceTaskNodeCollector(),
                new UserTaskNodeCollector(),
                new GatewayNodeCollector(),
                new StartEventNodeCollector(),
                new EndEventNodeCollector(),
                new BusinessTaskRuleNodeCollector());
    }
}
