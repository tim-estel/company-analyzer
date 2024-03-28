package com.estel.analyzer.infra.persistence.mapper;

import com.estel.analyzer.domain.model.Employee;

public class EmployeeMapper {

  public Employee toDomainObject(String[] fields) {
    Integer id = Integer.parseInt(fields[0]);
    String maybeManagerId = fields[4];
    Integer managerId = maybeManagerId.isBlank() ? null : Integer.parseInt(maybeManagerId);
    String firstName = fields[1];
    String lastName = fields[2];
    Integer salary = Integer.parseInt(fields[3]);
    return new Employee(
        id,
        managerId,
        firstName,
        lastName,
        salary
    );
  }
}
