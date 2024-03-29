package com.estel.analyzer.domain.model;

import java.util.Map;
import java.util.Set;

public record Organization(

    Map<Integer, Set<Node<Employee>>> employeesByLevel,
    Node<Employee> ceo
) {
}