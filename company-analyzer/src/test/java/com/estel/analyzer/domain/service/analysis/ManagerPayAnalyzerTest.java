package com.estel.analyzer.domain.service.analysis;

import com.estel.analyzer.configuration.Configuration;
import com.estel.analyzer.domain.model.Employee;
import com.estel.analyzer.domain.model.Organization;
import com.estel.analyzer.domain.service.organization.OrganizationService;
import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ManagerPayAnalyzerTest {

  private Configuration configuration = Configuration.Builder.newInstance()
      .minManagerToMeanReportPayRatio(1)
      .maxManagerToMeanReportPayRatio(2.5)
      .build();
  private OrganizationService organizationService = new OrganizationService();
  private Analyzer managerPayAnalyzer = new ManagerPayAnalyzer(configuration);

  @Test
  public void shouldFindManagersEarningTooLittle() {
    // given
    List<Employee> employees = List.of(
        new Employee(1, null, "Andrew", "Anderson", 28000),
        new Employee(2576, 1, "Boris", "Bowman", 20000),
        new Employee(2457, 2576, "Caroline", "Cartman", 35_000),
        new Employee(85788, 1, "Diane", "Doe", 20_000),
        new Employee(809, 2457, "Edward", "Emmerson", 10_000)
    );
    // TODO: Stop using a production feature (the org service i.e. the list -> tree mapper) in tests.
    // While there are tests for it, it's still not safe to do this.
    Organization organization = organizationService.createOrganizationStructure(employees);

    // when
    Map<IssueType, Set<Issue>> analysisResult = managerPayAnalyzer.analyze(organization);

    // then
    Set<Issue> managerUnderpayments = analysisResult.get(IssueType.PAY_TOO_LOW_RELATIVE_TO_REPORTS);
    assertEquals(1, managerUnderpayments.size());
    Issue singleManagerUnderpayment = managerUnderpayments.iterator().next();
    assertEquals(employees.get(1), singleManagerUnderpayment.employee());
    assertEquals(15_000, singleManagerUnderpayment.issueMagnitude());
  }

  @Test
  public void shouldFindManagersEarningTooMuch() {
    // given
    List<Employee> employees = List.of(
        new Employee(1, null, "Andrew", "Anderson", 120_005),
        new Employee(2576, 1, "Boris", "Bowman", 40_001),
        new Employee(2457, 1, "Caroline", "Cartman", 35_000),
        new Employee(85788, 1, "Diane", "Doe", 20_000),
        new Employee(809, 2457, "Edward", "Emmerson", 10_000)
    );
    // TODO: Stop using a production feature (the org service i.e. the list -> tree mapper) in tests.
    // While there are tests for it, it's still not safe to do this.
    Organization organization = organizationService.createOrganizationStructure(employees);

    // when
    Map<IssueType, Set<Issue>> analysisResult = managerPayAnalyzer.analyze(organization);

    // then
    List<Issue> managerOverpayments = analysisResult.get(IssueType.PAY_TOO_HIGH_RELATIVE_TO_REPORTS)
        .stream()
        .sorted(Comparator.comparingDouble(Issue::issueMagnitude).reversed())
        .toList();
    assertEquals(2, managerOverpayments.size());
    Issue ceoOverpayment = managerOverpayments.getFirst();
    assertEquals(employees.getFirst(), ceoOverpayment.employee());
    assertEquals(40837.5, ceoOverpayment.issueMagnitude());
    Issue otherManagerOverpayment = managerOverpayments.get(1);
    assertEquals(employees.get(2), otherManagerOverpayment.employee());
    assertEquals(10000.0, otherManagerOverpayment.issueMagnitude());
  }
}
