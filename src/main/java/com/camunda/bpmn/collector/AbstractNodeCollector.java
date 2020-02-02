package com.camunda.bpmn.collector;

import com.camunda.bpmn.domain.Node;
import com.camunda.bpmn.domain.Sequence;
import com.camunda.bpmn.util.Seq;
import org.camunda.bpm.model.xml.instance.DomElement;
import org.camunda.bpm.model.xml.instance.ModelElementInstance;

import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static java.util.Collections.emptyList;

public abstract class AbstractNodeCollector implements NodeCollector {
    protected final Predicate<DomElement> IS_OUTGOING_SEQUENCE = element -> "outgoing".equalsIgnoreCase(element.getLocalName());
    protected final Predicate<DomElement> IS_INCOMING_SEQUENCE = element -> "incoming".equalsIgnoreCase(element.getLocalName());

    protected List<Node> getNodes(Collection<ModelElementInstance> userTaskInstances, List<Sequence> sequences) {
        return userTaskInstances.stream()
                .map(ModelElementInstance::getDomElement)
                .map(domElement -> getNode(domElement, sequences))
                .collect(Collectors.toList());
    }

    private Node getNode(DomElement element, List<Sequence> sequences) {
        List<DomElement> outgoing = getFilteredSequence(element.getChildElements(), IS_OUTGOING_SEQUENCE);
        List<DomElement> incoming = getFilteredSequence(element.getChildElements(), IS_INCOMING_SEQUENCE);
        return buildNode(element.getAttribute("id"), outgoing, incoming, sequences);
    }

    private Node buildNode(String nodeId, List<DomElement> outgoing, List<DomElement> incoming, List<Sequence> sequences) {
        List<Sequence> outgoingSequences = getNodeSequences(outgoing, sequences);
        List<Sequence> incomingSequences = getNodeSequences(incoming, sequences);
        return new Node(nodeId, incomingSequences, outgoingSequences);
    }

    private List<Sequence> getNodeSequences(List<DomElement> items, List<Sequence> sequences) {
        if (items.isEmpty()) {
            return emptyList();
        }
        List<String> sequencesIds = Seq.map(items, DomElement::getTextContent);
        return sequences.stream()
                .filter(sequence -> sequencesIds.contains(sequence.getId()))
                .collect(Collectors.toList());
    }

    private List<DomElement> getFilteredSequence(List<DomElement> domElements, Predicate<DomElement> filter) {
        return domElements.stream()
                .filter(filter)
                .collect(Collectors.toList());
    }
}
