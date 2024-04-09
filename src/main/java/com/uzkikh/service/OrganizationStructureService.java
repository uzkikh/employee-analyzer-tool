package com.uzkikh.service;

import com.uzkikh.model.Employee;

import java.util.List;

public interface OrganizationStructureService {

    List<Employee> getAllEmployees();

    List<Employee> getEmployeeDirectSubordinates(int employeeId);

    int getEmployeeReportingLineLength(int employeeId);
}
