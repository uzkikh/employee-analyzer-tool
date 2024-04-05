package com.uzkikh;

import com.uzkikh.model.Employee;
import com.uzkikh.parser.CsvFileParser;
import com.uzkikh.parser.CsvFileParserImpl;
import com.uzkikh.service.OrganizationStructureService;
import com.uzkikh.service.OrganizationStructureServiceImpl;
import com.uzkikh.service.SalaryService;
import com.uzkikh.service.SalaryServiceImpl;

import java.math.BigDecimal;
import java.util.List;

public class EmployeeAnalyzer {

    private static final int MAX_REPORTING_LENGTH = 4;

    public static void main(String[] args) {
        checkProgramRequiredArgs(args);

        List<Employee> employees = parseCsv(args[0]);

        identifyPotentialImprovements(employees);
    }

    private static void identifyPotentialImprovements(List<Employee> employees) {
        OrganizationStructureService organizationStructureService = new OrganizationStructureServiceImpl(employees);
        SalaryService salaryService = new SalaryServiceImpl();

        System.out.println("Results of the analysis of the company structure and current salaries:");
        organizationStructureService.findAllEmployees()
                .forEach(employee -> {
                    analyzeEmployeeSalary(employee, organizationStructureService, salaryService);
                    analyzeReportingLength(employee, organizationStructureService);
                });
    }

    private static void analyzeReportingLength(Employee employee, OrganizationStructureService organizationStructure) {
        int reportingLength = organizationStructure.getEmployeeReportingLineLength(employee.getId());
        if (reportingLength > MAX_REPORTING_LENGTH) {
            System.out.println(String.format("%s has reporting length=%d, longer than required by %d",
                    employee, reportingLength, (reportingLength - MAX_REPORTING_LENGTH)));
        }
    }

    private static void analyzeEmployeeSalary(Employee employee, OrganizationStructureService organizationStructure,
                                              SalaryService salaryService) {
        List<Employee> directSubordinates = organizationStructure.findEmployeeDirectSubordinates(employee.getId());
        BigDecimal change = salaryService.calculateRequiredSalaryChange(employee, directSubordinates);
        if (change.compareTo(BigDecimal.ZERO) != 0) {
            System.out.println(String.format("%s salary must be adjusted by %s", employee, change));
        }
    }

    private static List<Employee> parseCsv(String csvFileName) {
        CsvFileParser csvFileParser = new CsvFileParserImpl();
        return csvFileParser.parseCsv(csvFileName);
    }

    private static void checkProgramRequiredArgs(String[] args) {
        if (args.length == 0) {
            System.out.println("Unable to start the program. The required parameter \"filename\" is not specified");
            System.exit(1);
        }
    }
}
