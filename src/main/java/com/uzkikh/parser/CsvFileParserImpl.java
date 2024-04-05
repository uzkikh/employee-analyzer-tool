package com.uzkikh.parser;

import com.uzkikh.model.Employee;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CsvFileParserImpl implements CsvFileParser {

    // Let's assume that we have a strictly defined csv file format with the column separator ","
    private static final String CSV_COLUMN_SEPARATOR = ",";

    @Override
    public List<Employee> parseCsv(String fileName) {
        Objects.requireNonNull(fileName, "Csv file name must be not null");
        List<Employee> employees = new ArrayList<>();

        String line;
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            br.readLine(); // assume that the source csv contains header and we need to skip it
            while ((line = br.readLine()) != null) {
                employees.add(extractEmployee(line));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.printf("File \"%s\" processed successfully. Number of records in the file = %d%n%n",
                fileName,
                employees.size());
        return employees;
    }

    private Employee extractEmployee(String line) {
        String[] lineValues = line.split(CSV_COLUMN_SEPARATOR);

        int id = Integer.parseInt(lineValues[0]);
        String firstName = lineValues[1];
        String lastName = lineValues[2];
        BigDecimal salary = new BigDecimal(lineValues[3]);
        int managerId = lineValues.length == 5 ? Integer.parseInt(lineValues[4]) : -1; // For CEO, the managerId == -1

        return new Employee(id, firstName, lastName, salary, managerId);
    }
}
