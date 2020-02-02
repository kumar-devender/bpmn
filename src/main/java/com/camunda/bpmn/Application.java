package com.camunda.bpmn;

import com.camunda.bpmn.config.BpmnEnginConfig;

public class Application {
    public static void main(String[] args) {
        if (args.length == 2) {
            BpmnEnginConfig.getBpmnPathEngin()
                    .findPath(args[0], args[1]);
        }
    }
}
