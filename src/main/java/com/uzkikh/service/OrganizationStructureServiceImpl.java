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
    public List<Employee> findEmployeeDirectSubordinates(int employeeId) {
        return Optional.ofNullable(idToSubordinates.get(employeeId))
                .orElse(Collections.emptyList());
    }

    @Override
    public List<Employee> findAllEmployees() {
        return new ArrayList<>(idToEmployee.values());
    }

    @Override
    public int getEmployeeReportingLineLength(int employeeId) {
        int length = 0;
        Employee employee = idToEmployee.get(employeeId);
        while (employee != null && employee.getManagerId() != -1) {
            employeeId = employee.getManagerId();
            employee = idToEmployee.get(employeeId);
            length++;
        }
        return length;
    }
}
