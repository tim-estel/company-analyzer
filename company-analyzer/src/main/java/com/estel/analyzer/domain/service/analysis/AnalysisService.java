package com.estel.analyzer.domain.service.analysis;

import com.estel.analyzer.domain.model.Organization;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class AnalysisService {

  private List<Analyzer> analyzers;

  public AnalysisService(List<Analyzer> analyzers) {
    this.analyzers = analyzers;
  }

  public Map<IssueType, Set<Issue>> analyze(Organization organization) {
    Map<IssueType, Set<Issue>> analysisResults = null;
    return analysisResults;
  }
}
