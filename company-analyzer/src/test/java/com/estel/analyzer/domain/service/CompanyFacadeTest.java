package com.estel.analyzer.domain.service;

import com.estel.analyzer.configuration.BeanContainer;
import com.estel.analyzer.domain.service.analysis.Issue;
import com.estel.analyzer.domain.service.analysis.IssueType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CompanyFacadeTest {

  private CompanyFacade companyFacade = BeanContainer.getInstance().companyFacade;

  /**
   * Normally, the test data should be clearly visible in the test definition.
   * However, this is intended to imitate an E2E test.
   * Therefore, the data has been placed in test/resources/test-company.csv.
   * TODO: add more E2E tests / unit tests.
   */
  @Test
  public void shouldAnalyzeCompanyBasedOnCsvFile() {
    // given
    String filePath = "test-company.csv";

    // when
    Map<IssueType, Set<Issue>> issues = companyFacade.analyzeCompany(filePath);

    // then
    Set<Issue> underpayments = issues.get(IssueType.PAY_TOO_LOW_RELATIVE_TO_REPORTS);
    assertEquals(1, underpayments.size());
    Issue underpayment = underpayments.iterator().next();
    assertEquals(6_000, underpayment.issueMagnitude());
    assertEquals("Alice", underpayment.employee().firstName());

    Set<Issue> overpayments = issues.get(IssueType.PAY_TOO_HIGH_RELATIVE_TO_REPORTS);
    Assertions.assertTrue(overpayments.isEmpty());

    Set<Issue> longReportingChains = issues.get(IssueType.REPORTING_CHAIN_TOO_LONG);
    Assertions.assertEquals(1, longReportingChains.size());
    Issue longReportingChain = longReportingChains.iterator().next();
    assertEquals(1, longReportingChain.issueMagnitude());
    assertEquals("Benjamin", longReportingChain.employee().firstName());
  }
}
