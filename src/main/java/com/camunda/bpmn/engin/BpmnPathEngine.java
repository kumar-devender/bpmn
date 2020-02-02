package com.camunda.bpmn.engin;

import com.camunda.bpmn.BpmnModelReader;
import com.camunda.bpmn.collector.NodeCollector;
import com.camunda.bpmn.collector.SequenceFlowCollector;
import com.camunda.bpmn.domain.Node;
import com.camunda.bpmn.domain.Sequence;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Stack;
import java.util.stream.Collectors;

import static java.util.Optional.empty;
import static java.util.Optional.ofNullable;

public class BpmnPathEngine {

    private SequenceFlowCollector sequenceFlowCollector;
    private List<NodeCollector> nodeCollectors;

    public BpmnPathEngine(SequenceFlowCollector sequenceFlowCollector, List<NodeCollector> nodeCollectors) {
        this.sequenceFlowCollector = sequenceFlowCollector;
        this.nodeCollectors = nodeCollectors;
    }

    public void findPath(String nextNode, String finishNode) {
        List<Node> nodes = getNodes();
        Stack<Node> nodeStack = new Stack<>();

        Node sourceNode = getNodeById(nextNode, nodes);
        while (true) {
            if (sourceNode == null) {
                break;
            }

            if (nodeStack.contains(sourceNode) == false) {
                nodeStack.push(sourceNode);
            }

            //found target node
            if (sourceNode.getId().equals(finishNode)) {
                break;
            }

            //reach to end node of the branch
            if (sourceNode.getOutgoing().size() == 0) {
                moveBackToGatewayNode(nodeStack);
                sourceNode = nodeStack.peek();
            }

            Optional<String> nextNodeOp = getNextNode(sourceNode, nodeStack);
            if (nextNodeOp.isPresent()) {
                nextNode = nextNodeOp.get();
            } else {
                break;
            }

            //move back to gateway node if current branch making loop
            for (Node node : nodeStack) {
                if (node.getId().equals(nextNode)) {
                    moveBackToGatewayNode(nodeStack);
                    nextNode = nodeStack.peek().getId();
                    break;
                }
            }

            sourceNode = getNodeById(nextNode, nodes);
        }
        System.out.println(nodeStack);
    }

    private Optional<String> getNextNode(Node sourceNode, Stack<Node> nodeStack) {
        if (sourceNode.isGatewayNode()) {
            return getGatewayNextNode(sourceNode, nodeStack);
        } else {
            return ofNullable(sourceNode.getOutgoing().get(0).getTargetRef());
        }
    }

    private Optional<String> getGatewayNextNode(Node sourceNode, Stack<Node> nodeStack) {
        Optional<Sequence> edge = sourceNode.getOutgoing()
                .stream()
                .filter(sequence -> sequence.isVisited() == false)
                .findFirst();

        //all branches of this gateway has been already visited so move to previous gateway
        if (edge.isPresent() == false) {
            moveBackToGatewayNode(nodeStack);
            if (nodeStack.size() == 0) {
                return empty();
            }
            sourceNode = nodeStack.peek();
        }

        return sourceNode.getOutgoing()
                .stream()
                .filter(sequence -> sequence.isVisited() == false)
                .peek(sequence -> sequence.setVisited(true))
                .map(Sequence::getTargetRef)
                .findFirst();
    }

    private void moveBackToGatewayNode(Stack<Node> nodeStack) {
        if (nodeStack.size() == 0) {
            return;
        }
        Node node = nodeStack.pop();
        while (nodeStack.size() != 0 && node.isGatewayNode() == false) {
            node = nodeStack.pop();
        }
        boolean hasNonVisitedBranch = node.getOutgoing().stream().anyMatch(sequence -> sequence.isVisited() == false);

        if (hasNonVisitedBranch) {
            nodeStack.push(node);
        } else {
            moveBackToGatewayNode(nodeStack);
        }
    }

    private Node getNodeById(String startNode, List<Node> nodes) {
        return nodes.stream()
                .filter(node -> startNode.equalsIgnoreCase(node.getId()))
                .findFirst()
                .orElse(null);
    }

    private List<Node> getNodes() {
        return BpmnModelReader.buildBpmnModelInstance()
                .map(modelInstance -> {
                    List<Sequence> sequences = sequenceFlowCollector.getSequences(modelInstance);
                    return this.nodeCollectors.stream()
                            .map(nodeCollector -> nodeCollector.getNodes(modelInstance, sequences))
                            .flatMap(List::stream)
                            .collect(Collectors.toList());
                }).orElse(Collections.emptyList());

    }
}
