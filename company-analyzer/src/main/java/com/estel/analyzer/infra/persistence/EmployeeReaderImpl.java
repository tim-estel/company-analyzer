package com.estel.analyzer.infra.persistence;

import com.estel.analyzer.domain.model.Employee;
import com.estel.analyzer.domain.persistence.EmployeeReader;
import com.estel.analyzer.infra.io.CsvReader;
import com.estel.analyzer.infra.persistence.mapper.EmployeeMapper;

import java.util.List;

public class EmployeeReaderImpl implements EmployeeReader {
  @Override
  public List<Employee> readEmployees(String employeeLocation) {
    CsvReader reader = new CsvReader();
    EmployeeMapper mapper = new EmployeeMapper();
    List<Employee> employees = reader.readCsv(employeeLocation)
        .stream()
        .map(mapper::toDomainObject)
        .toList();
    return employees;
  }
}
