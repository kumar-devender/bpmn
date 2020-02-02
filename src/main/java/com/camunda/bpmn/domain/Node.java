package com.camunda.bpmn.domain;

import java.util.List;

public class Node {
    private String id;
    private List<Sequence> incoming;
    private List<Sequence> outgoing;
    private boolean gatewayNode;

    public Node(String id, List<Sequence> incoming, List<Sequence> outgoing) {
        this.id = id;
        this.incoming = incoming;
        this.outgoing = outgoing;
    }

    public String getId() {
        return id;
    }

    public List<Sequence> getIncoming() {
        return incoming;
    }

    public List<Sequence> getOutgoing() {
        return outgoing;
    }

    public void setGatewayNode(boolean gatewayNode) {
        this.gatewayNode = gatewayNode;
    }

    public boolean isGatewayNode() {
        return gatewayNode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return id.equals(node.id);
    }

    @Override
    public String toString() {
        return id;
    }
}
