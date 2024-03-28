package com.estel.analyzer.domain.service.analysis;

import com.estel.analyzer.domain.model.Organization;

import java.util.Map;
import java.util.Set;

public interface Analyzer {

  Map<IssueType, Set<Issue>> analyze(Organization organization);
}
