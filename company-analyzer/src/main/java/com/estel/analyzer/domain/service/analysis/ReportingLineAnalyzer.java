package com.estel.analyzer.domain.service.analysis;

import com.estel.analyzer.configuration.Configuration;
import com.estel.analyzer.domain.model.Employee;
import com.estel.analyzer.domain.model.Node;
import com.estel.analyzer.domain.model.Organization;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class ReportingLineAnalyzer implements Analyzer {

  private Configuration configuration;

  public ReportingLineAnalyzer(Configuration configuration) {
    this.configuration = configuration;
  }

  @Override
  public Map<IssueType, Set<Issue>> analyze(Organization organization) {
    Map<Integer, Set<Node<Employee>>> employeesByLevel = organization.employeesByLevel();
    Set<Issue> employeesWithLongSupervisorChains = employeesByLevel
        .keySet()
        .stream()
        .filter(it -> it > configuration.MAX_REPORTING_LINE_LENGTH())
        .flatMap(it -> employeesByLevel.get(it).stream())
        .map(this::toReportingLineIssue)
        .collect(Collectors.toSet());
    return Map.of(
        IssueType.REPORTING_CHAIN_TOO_LONG,
        employeesWithLongSupervisorChains
    );
  }

  private Issue toReportingLineIssue(Node<Employee> employeeNode) {
    return new Issue(
        employeeNode.data,
        IssueType.REPORTING_CHAIN_TOO_LONG,
        employeeNode.distanceFromRoot - configuration.MAX_REPORTING_LINE_LENGTH()
    );
  }
}
