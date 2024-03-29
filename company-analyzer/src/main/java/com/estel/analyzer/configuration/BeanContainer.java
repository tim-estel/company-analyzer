package com.estel.analyzer.configuration;

import com.estel.analyzer.domain.persistence.EmployeeReader;
import com.estel.analyzer.domain.service.CompanyFacade;
import com.estel.analyzer.domain.service.analysis.AnalysisService;
import com.estel.analyzer.domain.service.analysis.ManagerPayAnalyzer;
import com.estel.analyzer.domain.service.analysis.ReportingLineAnalyzer;
import com.estel.analyzer.domain.service.organization.OrganizationService;
import com.estel.analyzer.infra.persistence.EmployeeReaderImpl;

import java.util.List;

public class BeanContainer {

  private static BeanContainer INSTANCE;

  private BeanContainer() {
  }

  public static BeanContainer getInstance() {
    if (INSTANCE == null) {
      INSTANCE = new BeanContainer();
    }
    return INSTANCE;
  }

  public final Configuration configuration = Configuration.Builder.newInstance().build();
  public final EmployeeReader employeeReader = new EmployeeReaderImpl();
  public final OrganizationService organizationService = new OrganizationService();
  public final ManagerPayAnalyzer managerPayAnalyzer = new ManagerPayAnalyzer(
      configuration
  );
  public final ReportingLineAnalyzer reportingLineAnalyzer = new ReportingLineAnalyzer(
      configuration
  );
  public final AnalysisService analysisService = new AnalysisService(
      List.of(
          managerPayAnalyzer,
          reportingLineAnalyzer
      )
  );
  public final CompanyFacade companyFacade = new CompanyFacade(
      employeeReader,
      organizationService,
      analysisService
  );
}
