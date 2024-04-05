package com.uzkikh.service;

import com.uzkikh.model.Employee;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SalaryServiceImplTest {

    private static SalaryServiceImpl salaryService;

    @BeforeAll
    static void beforeAll() {
        salaryService = new SalaryServiceImpl();
    }

    @Test
    void testDoNotNeedToIncreaseSalaryIfCurrentInPossibleRange() {
        Employee employee = new Employee(100, "X", "Y", BigDecimal.valueOf(55000), 10);
        List<Employee> subordinates = List.of(
                new Employee(101, "A", "B", BigDecimal.valueOf(40000), 100),
                new Employee(102, "C", "D", BigDecimal.valueOf(45000), 100),
                new Employee(103, "E", "F", BigDecimal.valueOf(50000), 100)
        );

        BigDecimal change = salaryService.calculateRequiredSalaryChange(employee, subordinates);

        assertEquals(BigDecimal.ZERO, change);
    }

    @Test
    void testDoNotNeedToIncreaseSalaryIfNoSubordinates() {
        Employee employee = new Employee(100, "X", "Y", BigDecimal.valueOf(50000), 10);
        List<Employee> subordinates = List.of();

        BigDecimal change = salaryService.calculateRequiredSalaryChange(employee, subordinates);

        assertEquals(BigDecimal.ZERO, change);
    }

    @Test
    void testNeedToIncreaseSalaryIfLowerThanMinRequired() {
        Employee employee = new Employee(100, "X", "Y", BigDecimal.valueOf(48000), 10);
        List<Employee> subordinates = List.of(
                new Employee(101, "A", "B", BigDecimal.valueOf(40000), 100),
                new Employee(102, "C", "D", BigDecimal.valueOf(45000), 100),
                new Employee(103, "E", "F", BigDecimal.valueOf(50000), 100)
        );

        BigDecimal change = salaryService.calculateRequiredSalaryChange(employee, subordinates);

        assertEquals(BigDecimal.valueOf(6000).setScale(2, RoundingMode.HALF_EVEN), change);
    }

    @Test
    void testNeedToDecreaseSalaryIfGreaterThanMaxRequired() {
        Employee employee = new Employee(100, "X", "Y", BigDecimal.valueOf(69000), 10);
        List<Employee> subordinates = List.of(
                new Employee(101, "A", "B", BigDecimal.valueOf(40000), 100),
                new Employee(102, "C", "D", BigDecimal.valueOf(45000), 100),
                new Employee(103, "E", "F", BigDecimal.valueOf(50000), 100)
        );

        BigDecimal change = salaryService.calculateRequiredSalaryChange(employee, subordinates);

        assertEquals(BigDecimal.valueOf(-1500).setScale(2, RoundingMode.HALF_EVEN), change);
    }

}