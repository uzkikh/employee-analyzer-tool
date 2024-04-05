package com.uzkikh.service;

import com.uzkikh.model.Employee;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OrganizationStructureServiceImplTest {

    @Test
    void testShouldReturnEmptyIfOrganizationIsEmpty() {
        OrganizationStructureServiceImpl service = new OrganizationStructureServiceImpl(List.of());

        assertEquals(Collections.emptyList(), service.findAllEmployees());
        assertEquals(Collections.emptyList(), service.findEmployeeDirectSubordinates(100));
    }

    @Test
    void testShouldReturnZeroLengthIfOrganizationIsEmpty() {
        OrganizationStructureServiceImpl service = new OrganizationStructureServiceImpl(List.of());

        assertEquals(0, service.getEmployeeReportingLineLength(100));
    }

    @Test
    void testShouldReturnEmployees() {
        List<Employee> employees = List.of(
                new Employee(100, "A", "B", BigDecimal.valueOf(20000), -1),
                new Employee(101, "C", "D", BigDecimal.valueOf(15000), 100),
                new Employee(102, "E", "F", BigDecimal.valueOf(12000), 100),
                new Employee(103, "G", "H", BigDecimal.valueOf(18000), 101)
        );

        OrganizationStructureServiceImpl service = new OrganizationStructureServiceImpl(employees);

        assertEquals(employees, service.findAllEmployees());
    }

    @Test
    void testShouldReturnDirectSubordinates() {
        List<Employee> employees = List.of(
                new Employee(100, "A", "B", BigDecimal.valueOf(20000), -1),
                new Employee(101, "C", "D", BigDecimal.valueOf(15000), 100),
                new Employee(102, "E", "F", BigDecimal.valueOf(12000), 100),
                new Employee(103, "G", "H", BigDecimal.valueOf(18000), 101)
        );
        List<Employee> expected = List.of(
                new Employee(101, "C", "D", BigDecimal.valueOf(15000), 100),
                new Employee(102, "E", "F", BigDecimal.valueOf(12000), 100)
        );

        OrganizationStructureServiceImpl service = new OrganizationStructureServiceImpl(employees);

        assertEquals(expected, service.findEmployeeDirectSubordinates(100));
    }

    @Test
    void testShouldReturnReportingLineLength() {
        List<Employee> employees = List.of(
                new Employee(100, "A", "B", BigDecimal.valueOf(20000), -1),
                new Employee(101, "C", "D", BigDecimal.valueOf(15000), 100),
                new Employee(102, "E", "F", BigDecimal.valueOf(12000), 100),
                new Employee(103, "G", "H", BigDecimal.valueOf(18000), 101)
        );

        OrganizationStructureServiceImpl service = new OrganizationStructureServiceImpl(employees);

        assertEquals(2, service.getEmployeeReportingLineLength(103));
    }


}