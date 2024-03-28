package com.estel.analyzer.infra.app;


import com.estel.analyzer.configuration.BeanContainer;
import com.estel.analyzer.domain.service.analysis.Issue;
import com.estel.analyzer.domain.service.analysis.IssueType;
import com.estel.analyzer.infra.io.AnalysisPresenter;

import java.util.Map;
import java.util.Set;

public class CompanyAnalyzerApp {
  private static final BeanContainer beans = BeanContainer.getInstance();

  public static void main(String[] args) {
    String filePath = getFilePath(args);
    Map<IssueType, Set<Issue>> analysisResult = beans.companyFacade.analyzeCompany(filePath);
    AnalysisPresenter presenter = new AnalysisPresenter();
    presenter.presentAnalysisResults(analysisResult);
  }

  private static String getFilePath(String[] args) {
    if (args != null && args.length > 0) {
      return args[0];
    } else {
      return beans.configuration.DEFAULT_FILE_PATH();
    }
  }
}
