package com.estel.analyzer.infra.io;

import com.estel.analyzer.domain.service.analysis.Issue;
import com.estel.analyzer.domain.service.analysis.IssueType;

import java.util.Map;
import java.util.Set;

public class AnalysisPresenter {

  public void presentAnalysisResults(Map<IssueType, Set<Issue>> issues) {
    System.out.println("Hello world");
  }
}
