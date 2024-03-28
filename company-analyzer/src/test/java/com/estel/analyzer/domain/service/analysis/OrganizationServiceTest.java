package com.estel.analyzer.domain.service.analysis;

import com.estel.analyzer.domain.model.Employee;
import com.estel.analyzer.domain.model.Node;
import com.estel.analyzer.domain.model.Organization;
import com.estel.analyzer.domain.service.organization.OrganizationService;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OrganizationServiceTest {

  private final OrganizationService organizationService = new OrganizationService();

  @Test
  public void shouldTransformListOfEmployeesIntoTree() {
    // given
    Employee ceo = new Employee(1, null, "Andrew", "Anderson", 60_000);
    List<Employee> midLevelEmployees = List.of(
        new Employee(2576, 1, "Boris", "Bowman", 1),
        new Employee(2457, 1, "Caroline", "Cartman", 68_954_734),
        new Employee(85788, 1, "Diane", "Doe", 420)
    );
    Employee janitor = new Employee(809, 2457, "Edward", "Emmerson", 6900);
    List<Employee> flatEmployeeList = List.of(
        ceo,
        midLevelEmployees.get(0),
        midLevelEmployees.get(1),
        midLevelEmployees.get(2),
        janitor
    );

    // when
    Organization organization = organizationService.createOrganizationStructure(flatEmployeeList);

    // then
    // TODO: make the list of checks complete, split into multiple test cases.
    Node<Employee> detectedCEO = getFirstEmployeeWithSeniority(organization, 0);
    Set<Node<Employee>> detectedMidLevelWorkers = organization.employeesBySeniority().get(1);
    Node<Employee> detectedJanitor = getFirstEmployeeWithSeniority(organization, 2);

    assertEquals("Andrew", detectedCEO.data.firstName(), "Incorrectly detected CEO");
    assertEquals(3, organization.employeesBySeniority().size(), "Incorrectly calculated the height of the org structure");
    assertEquals(0, detectedCEO.distanceFromRoot, "CEO has superiors");
    assertEquals(3, detectedCEO.children.size(), "CEO has an incorrect number of direct reports");

    assertEquals(3, detectedMidLevelWorkers.size(), "Incorrect number of mid-level employees");
    assertEquals(1, detectedMidLevelWorkers.stream().filter(it -> !it.children.isEmpty()).count(), "Incorrect number of mid-level employees with subordinates");
    assertEquals("Caroline", detectedMidLevelWorkers.stream().filter(it -> !it.children.isEmpty()).findFirst().get().data.firstName(), "The janitor has an incorrect manager");
    for (Node<Employee> midLevelWorker : detectedMidLevelWorkers) {
      assertEquals(ceo.id(), midLevelWorker.parent.data.id(), "Mid-level worker " + midLevelWorker.data.firstName() + " does not report to CEO");
    }

    assertEquals("Emmerson", detectedJanitor.data.lastName(), "Incorrectly detected janitor");
    assertEquals(2, detectedJanitor.distanceFromRoot, "Janitor has an incorrect length of the reporting chain");
    assertEquals(0, detectedJanitor.children.size(), "Janitor has subordinates");
    assertEquals(ceo.id(), detectedJanitor.parent.parent.data.id(), "Cannot get from the janitor to CEO via the reporting chain");
  }

  private Node<Employee> getFirstEmployeeWithSeniority(
      Organization organization,
      Integer seniority
  ) {
    return organization.employeesBySeniority().get(seniority).iterator().next();
  }
}