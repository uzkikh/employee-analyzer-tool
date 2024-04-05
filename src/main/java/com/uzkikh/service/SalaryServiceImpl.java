package com.uzkikh.service;

import com.uzkikh.model.Employee;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;

public class SalaryServiceImpl implements SalaryService {

    private static final BigDecimal MIN_SALARY_RATIO = BigDecimal.valueOf(1.2);
    private static final BigDecimal MAX_SALARY_RATIO = BigDecimal.valueOf(1.5);

    public SalaryServiceImpl() {
    }

    @Override
    public BigDecimal calculateRequiredSalaryChange(Employee employee, List<Employee> subordinates) {
        Optional<BigDecimal> avgSubordinatesSalaryOpt = subordinates.stream()
                .map(Employee::getSalary)
                .reduce(BigDecimal::add)
                .map(total -> total.divide(BigDecimal.valueOf(subordinates.size()), RoundingMode.HALF_EVEN));
        if (avgSubordinatesSalaryOpt.isEmpty()) {
            return BigDecimal.ZERO;
        }

        BigDecimal minRequiredSalary = avgSubordinatesSalaryOpt.get().multiply(MIN_SALARY_RATIO);
        BigDecimal maxRequiredSalary = avgSubordinatesSalaryOpt.get().multiply(MAX_SALARY_RATIO);
        BigDecimal salary = employee.getSalary();

        if (salary.compareTo(minRequiredSalary) < 0) {
            return minRequiredSalary.subtract(salary).setScale(2, RoundingMode.HALF_EVEN);
        }

        if (salary.compareTo(maxRequiredSalary) > 0) {
            return salary.subtract(maxRequiredSalary).setScale(2, RoundingMode.HALF_EVEN).negate();
        }

        return BigDecimal.ZERO;
    }
}
