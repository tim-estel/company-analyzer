package com.estel.analyzer.domain.service.analysis;

import com.estel.analyzer.domain.model.Employee;

public record Issue(
    Employee employee,
    IssueType issueType,
    Number issueMagnitude
) {
}