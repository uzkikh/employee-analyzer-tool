package com.uzkikh.service;

import com.uzkikh.model.Employee;

import java.util.*;

public class OrganizationStructureServiceImpl implements OrganizationStructureService {

    private final Map<Integer, Employee> idToEmployee = new HashMap<>();
    private final Map<Integer, List<Employee>> idToSubordinates = new HashMap<>();

    public OrganizationStructureServiceImpl(List<Employee> employees) {
        employees.forEach(employee -> {
            idToEmployee.put(employee.getId(), employee);

            if (employee.getManagerId() != -1) { // CEO doesn't have a manager, so we omitted this value
                idToSubordinates.computeIfAbsent(employee.getManagerId(), k -> new ArrayList<>()).add(employee);
            }
        });
    }

    @Override
    public List<Employee> getEmployeeDirectSubordinates(int employeeId) {
        return Optional.ofNullable(idToSubordinates.get(employeeId))
                .orElse(Collections.emptyList());
    }

    @Override
    public List<Employee> getAllEmployees() {
        return new ArrayList<>(idToEmployee.values());
    }

    @Override
    public int getEmployeeReportingLineLength(int employeeId) {
        Employee employee = idToEmployee.get(employeeId);
        if (employee == null || employee.getManagerId() == -1) {
            return 0;
        } else {
            return 1 + getEmployeeReportingLineLength(employee.getManagerId());
        }
    }
}
