package com.estel.analyzer.domain.service.analysis;

import com.estel.analyzer.configuration.Configuration;
import com.estel.analyzer.domain.model.Employee;
import com.estel.analyzer.domain.model.Node;
import com.estel.analyzer.domain.model.Organization;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class ManagerPayAnalyzer implements Analyzer {

  private final Configuration configuration;

  public ManagerPayAnalyzer(Configuration configuration) {
    this.configuration = configuration;
  }

  @Override
  public Map<IssueType, Set<Issue>> analyze(Organization organization) {
    Map<Employee, Double> averageDirectReportPayPerManager = organization.employeesByLevel()
        .values()
        .stream()
        .flatMap(Collection::stream)
        .filter(it -> !it.children.isEmpty())
        .collect(Collectors.toMap(
            it -> it.data,
            this::averageDirectReportSalary
        ));
    Set<Issue> underpayments = getUnderpayments(averageDirectReportPayPerManager);
    Set<Issue> overpayments = getOverpayments(averageDirectReportPayPerManager);
    return Map.of(
        IssueType.PAY_TOO_LOW_RELATIVE_TO_REPORTS, underpayments,
        IssueType.PAY_TOO_HIGH_RELATIVE_TO_REPORTS, overpayments
    );
  }

  private Double averageDirectReportSalary(Node<Employee> employee) {
    return employee.children.stream()
        .mapToDouble(it -> it.data.salary())
        .average()
        .orElse(0);
  }

  private Set<Issue> getUnderpayments(Map<Employee, Double> averageDirectReportPayPerManager) {
    return averageDirectReportPayPerManager
        .entrySet()
        .stream()
        .map(it -> Map.entry(it.getKey(), it.getValue() * configuration.MIN_MANAGER_TO_MEAN_REPORT_PAY_RATIO() - it.getKey().salary()))
        .filter(it -> it.getValue() > 0)
        .map(this::toUnderpaymentIssue)
        .collect(Collectors.toSet());
  }

  private Set<Issue> getOverpayments(Map<Employee, Double> averageDirectReportPayPerManager) {
    return averageDirectReportPayPerManager
        .entrySet()
        .stream()
        .map(it -> Map.entry(it.getKey(), it.getKey().salary() - it.getValue() * configuration.MAX_REPORTING_LINE_LENGTH()))
        .filter(it -> it.getValue() > 0)
        .map(this::toOverpaymentIssue)
        .collect(Collectors.toSet());
  }

  private Issue toUnderpaymentIssue(Map.Entry<Employee, Double> underpaidEmployee) {
    return new Issue(
        underpaidEmployee.getKey(),
        IssueType.PAY_TOO_LOW_RELATIVE_TO_REPORTS,
        underpaidEmployee.getValue()
    );
  }

  private Issue toOverpaymentIssue(Map.Entry<Employee, Double> overpaidEmployee) {
    return new Issue(
        overpaidEmployee.getKey(),
        IssueType.PAY_TOO_HIGH_RELATIVE_TO_REPORTS,
        overpaidEmployee.getValue()
    );
  }
}
