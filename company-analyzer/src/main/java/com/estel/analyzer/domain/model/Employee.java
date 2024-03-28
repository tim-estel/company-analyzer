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
    return STR."Employee{id=\{id}, managerId=\{managerId}, firstName='\{firstName}\{'\''}, lastName='\{lastName}\{'\''}, salary=\{salary}\{'}'}";
  }
}
