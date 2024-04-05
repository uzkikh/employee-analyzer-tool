package com.uzkikh.service;

import com.uzkikh.model.Employee;

import java.math.BigDecimal;
import java.util.List;

public interface SalaryService {

    BigDecimal calculateRequiredSalaryChange(Employee employee, List<Employee> subordinates);
}
