package com.estel.analyzer.domain.service.analysis;

import com.estel.analyzer.configuration.Configuration;
import com.estel.analyzer.domain.model.Organization;

import java.util.Map;
import java.util.Set;

public class ManagerPayAnalyzer implements Analyzer {

  private Configuration configuration;

  public ManagerPayAnalyzer(Configuration configuration) {
    this.configuration = configuration;
  }

  @Override
  public Map<IssueType, Set<Issue>> analyze(Organization organization) {
    return null;
  }
}
