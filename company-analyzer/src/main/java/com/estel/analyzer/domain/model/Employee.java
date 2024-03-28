package com.estel.analyzer.domain.model;

public record Employee(
    Integer id,
    Integer managerId,
    String firstName,
    String lastName,
    Integer salary
) {
  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("Employee{");
    sb.append("id=").append(id);
    sb.append(", managerId=").append(managerId);
    sb.append(", firstName='").append(firstName).append('\'');
    sb.append(", lastName='").append(lastName).append('\'');
    sb.append(", salary=").append(salary);
    sb.append('}');
    return sb.toString();
  }
}
