package com.camunda.bpmn.domain;

public class Sequence {
    private String id;
    private String sourceRef;
    private String targetRef;
    private boolean visited;

    public Sequence(String id, String sourceRef, String targetRef) {
        this.id = id;
        this.sourceRef = sourceRef;
        this.targetRef = targetRef;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public String getId() {
        return id;
    }

    public String getSourceRef() {
        return sourceRef;
    }

    public String getTargetRef() {
        return targetRef;
    }

    @Override
    public String toString() {
        return id;
    }
}
