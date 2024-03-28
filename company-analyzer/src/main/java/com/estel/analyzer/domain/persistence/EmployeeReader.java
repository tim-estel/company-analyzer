package com.estel.analyzer.domain.persistence;

import com.estel.analyzer.domain.model.Employee;

import java.util.List;

public interface EmployeeReader {

  public List<Employee> readEmployees(String employeeLocation);
}
