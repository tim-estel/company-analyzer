package com.estel.analyzer.domain.model;

import java.util.Map;

public record Organization(
    Map<Integer, Node<Employee>> employees,
    Node<Employee> ceo
) {
}