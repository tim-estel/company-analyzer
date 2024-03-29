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

public class ReportingLineAnalyzerTest {
  private final Configuration configuration = Configuration.Builder.newInstance()
      .maxReportingLineLength(2)
      .build();
  private final OrganizationService organizationService = new OrganizationService();
  private final Analyzer reportingLineAnalyzer = new ReportingLineAnalyzer(configuration);

  @Test
  public void shouldFindTooLongReportingChains() {
    // given
    int paidInExposure = 0; // realism is crucial for a good test
    List<Employee> employees = List.of(
        new Employee(0, null, "Andrew", "Anderson", paidInExposure),
        new Employee(1, 0, "Boris", "Bowman", paidInExposure),
        new Employee(2, 1, "Caroline", "Cartman", paidInExposure),
        new Employee(3, 2, "Diane", "Doe", paidInExposure),
        new Employee(4, 3, "Tim", "Emmerson", paidInExposure)
    );
    // TODO: Stop using a production feature (the org service i.e. the list -> tree mapper) in tests.
    // While there are tests for it, it's still not safe to do this.
    Organization organization = organizationService.createOrganizationStructure(employees);

    // when
    Map<IssueType, Set<Issue>> analysisResult = reportingLineAnalyzer.analyze(organization);

    // then
    assertEquals(1, analysisResult.size());
    List<Issue> longReportingChains = analysisResult.get(IssueType.REPORTING_CHAIN_TOO_LONG)
        .stream()
        .sorted(Comparator.comparingInt(it -> it.issueMagnitude().intValue()))
        .toList();
    assertEquals(2, longReportingChains.size());

    assertEquals(1, longReportingChains.get(0).issueMagnitude());
    assertEquals("Doe", longReportingChains.get(0).employee().lastName());
    assertEquals(2, longReportingChains.get(1).issueMagnitude());
    assertEquals("Emmerson", longReportingChains.get(1).employee().lastName());
  }
}
