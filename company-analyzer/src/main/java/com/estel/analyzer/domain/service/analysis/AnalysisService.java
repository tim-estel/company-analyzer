package com.estel.analyzer.domain.service.analysis;

import com.estel.analyzer.domain.model.Organization;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class AnalysisService {

  private final List<Analyzer> analyzers;

  public AnalysisService(List<Analyzer> analyzers) {
    this.analyzers = analyzers;
  }

  public Map<IssueType, Set<Issue>> analyze(Organization organization) {
    Map<IssueType, Set<Issue>> combinedResults = new EnumMap<>(IssueType.class);
    analyzers.forEach(it -> {
      Map<IssueType, Set<Issue>> result = it.analyze(organization);
      combinedResults.putAll(result);
    });
    return combinedResults;
  }
}
