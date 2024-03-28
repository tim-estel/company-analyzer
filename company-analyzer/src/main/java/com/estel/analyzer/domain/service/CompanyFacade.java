package com.estel.analyzer.domain.service;

import com.estel.analyzer.domain.model.Employee;
import com.estel.analyzer.domain.model.Organization;
import com.estel.analyzer.domain.persistence.EmployeeReader;
import com.estel.analyzer.domain.service.analysis.AnalysisService;
import com.estel.analyzer.domain.service.analysis.Issue;
import com.estel.analyzer.domain.service.analysis.IssueType;
import com.estel.analyzer.domain.service.organization.OrganizationService;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class CompanyFacade {

  private EmployeeReader employeeReader;
  private OrganizationService organizationService;
  private AnalysisService analysisService;

  public CompanyFacade(
      EmployeeReader employeeReader,
      OrganizationService organizationService,
      AnalysisService analysisService
  ) {
    this.employeeReader = employeeReader;
    this.organizationService = organizationService;
    this.analysisService = analysisService;
  }

  public Map<IssueType, Set<Issue>> analyzeCompany(String companyStructurePath) {
    List<Employee> employees = employeeReader.readEmployees(companyStructurePath);
    Organization orgStructure = organizationService.createOrganizationStructure(employees);
    Map<IssueType, Set<Issue>> analysisResults = analysisService.analyze(orgStructure);
    return analysisResults;
  }
}
