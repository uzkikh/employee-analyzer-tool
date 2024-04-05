package com.uzkikh.service;

import com.uzkikh.model.Employee;

import java.util.List;

public interface OrganizationStructureService {

    List<Employee> findAllEmployees();

    List<Employee> findEmployeeDirectSubordinates(int employeeId);

    int getEmployeeReportingLineLength(int employeeId);
}
