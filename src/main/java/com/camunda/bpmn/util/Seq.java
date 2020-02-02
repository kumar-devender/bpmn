package com.camunda.bpmn.util;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;

import static java.util.stream.Collectors.toList;

public class Seq {
    public static <T, E> List<E> map(Collection<T> collection,
                                     Function<? super T, ? extends E> mapper) {
        return collection.stream().map(mapper).collect(toList());
    }
}
