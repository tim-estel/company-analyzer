package com.estel.analyzer.infra.io;

import com.estel.analyzer.configuration.Configuration;
import com.estel.analyzer.domain.service.analysis.Issue;
import com.estel.analyzer.domain.service.analysis.IssueType;

import java.util.Map;
import java.util.Set;

public class AnalysisPresenter {

  public void presentAnalysisResults(Configuration configuration, String fileName, Map<IssueType, Set<Issue>> analysisResults) {
    System.out.println("=========================================================================================");
    System.out.println("Performed an analysis of the org structure based on " + fileName);
    reportUnderpaidManagers(configuration, analysisResults);
    reportOverpaidManagers(configuration, analysisResults);
    reportExcessivelyLongReportingChains(configuration, analysisResults);
    System.out.println("=========================================================================================");
  }


  private void reportUnderpaidManagers(Configuration configuration, Map<IssueType, Set<Issue>> analysisResults) {
    System.out.println("======== Managers receiving less than " + (configuration.MIN_MANAGER_TO_MEAN_REPORT_PAY_RATIO()) + "x the average pay of their direct reports: ========");
    Set<Issue> issues = analysisResults.get(IssueType.PAY_TOO_LOW_RELATIVE_TO_REPORTS);
    if(issues.isEmpty()) {
      noIssuesDetected();
      return;
    }
    for(Issue issue: issues) {
      System.out.println(issue.employee().toString() + ": earning less by " + issue.issueMagnitude());
    }
  }

  private void reportOverpaidManagers(Configuration configuration, Map<IssueType, Set<Issue>> analysisResults) {
    System.out.println("======== Managers receiving more than " + (configuration.MAX_MANAGER_TO_MEAN_REPORT_PAY_RATIO()) + "x the average pay of their direct reports: ========");
    Set<Issue> issues = analysisResults.get(IssueType.PAY_TOO_HIGH_RELATIVE_TO_REPORTS);
    if(issues.isEmpty()) {
      noIssuesDetected();
      return;
    }
    for(Issue issue: issues) {
      System.out.println(issue.employee().toString() + ": earning more by " + issue.issueMagnitude());
    }
  }

  private void reportExcessivelyLongReportingChains(Configuration configuration, Map<IssueType, Set<Issue>> analysisResults) {
    System.out.println("======== Employees with >" + (configuration.MAX_REPORTING_LINE_LENGTH()-1) + " managers between them and the CEO: ========");
    Set<Issue> issues = analysisResults.get(IssueType.REPORTING_CHAIN_TOO_LONG);
    if(issues.isEmpty()) {
      noIssuesDetected();
      return;
    }
    for(Issue issue: issues) {
      System.out.println(issue.employee().toString() + ": max managers exceeded by " + issue.issueMagnitude());
    }
  }

  private void noIssuesDetected() {
    System.out.println("None detected!");
  }
}
