package com.estel.analyzer.domain.service.organization;

import com.estel.analyzer.domain.model.Employee;
import com.estel.analyzer.domain.model.Node;
import com.estel.analyzer.domain.model.Organization;

import java.util.*;
import java.util.stream.Collectors;

public class OrganizationService {

  public Organization createOrganizationStructure(List<Employee> employees) {
    Map<Integer, Node<Employee>> idToEmployee = new HashMap<>();
    Node<Employee> ceo = null;

    for (Employee employee : employees) {
      Node<Employee> node = new Node<>();
      node.data = employee;
      node.children = new ArrayList<>();
      node.distanceFromRoot = 0;
      idToEmployee.put(employee.id(), node);
      if (employee.managerId() == null) {
        ceo = node;
      }
    }

    for (Employee employee : employees) {
      if (employee.managerId() == null) {
        continue;
      }
      Node<Employee> employeeNode = idToEmployee.get(employee.id());
      Node<Employee> managerNode = idToEmployee.get(employee.managerId());
      employeeNode.parent = managerNode;
      managerNode.children.add(employeeNode);
    }

    // TODO: If files with over 1000 employees must be supported, simulate recursion in memory.
    setDistanceFromCEO(ceo, 0);

    Map<Integer, Set<Node<Employee>>> employeeNodesByDistanceFromCEO = idToEmployee.values()
        .stream()
        .collect(Collectors.groupingBy(it -> it.distanceFromRoot, Collectors.toSet()));

    return new Organization(
        employeeNodesByDistanceFromCEO,
        ceo
    );
  }

  private void setDistanceFromCEO(Node<Employee> node, int distance) {
    node.distanceFromRoot = distance;

    for (Node<Employee> child : node.children) {
      setDistanceFromCEO(child, distance + 1);
    }
  }
}
