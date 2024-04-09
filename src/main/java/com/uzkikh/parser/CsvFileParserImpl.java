package com.uzkikh.parser;

import com.uzkikh.exception.CsvParserException;
import com.uzkikh.model.Employee;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class CsvFileParserImpl implements CsvFileParser {

    // Let's assume that we have a strictly defined csv file format with the column separator "," and with a header.
    // This part can be easily improved if necessary by adding new cli arguments when running the program.
    private static final String CSV_COLUMN_SEPARATOR = ",";
    private static final boolean HAS_HEADER = true;

    @Override
    public List<Employee> parseCsv(String fileName) {
        if (fileName == null) {
            throw new CsvParserException("Csv file name must be not null");
        }

        List<Employee> employees = new ArrayList<>();
        String line;
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            if (HAS_HEADER) {
                br.readLine();
            }
            while ((line = br.readLine()) != null) {
                employees.add(extractEmployee(line));
            }
        } catch (IOException e) {
            throw new CsvParserException("Failed to parse csv file", e);
        }
        System.out.printf("File \"%s\" processed successfully. Number of records in the file = %d%n%n",
                fileName,
                employees.size());
        return employees;
    }

    private Employee extractEmployee(String line) {
        String[] lineValues = line.split(CSV_COLUMN_SEPARATOR);
        if (lineValues.length < 4) {
            throw new CsvParserException("There is not enough data to extract the employee in the line: \"%s\". Parsing stopped."
                    .formatted(line));
        }

        int id = Integer.parseInt(lineValues[0]);
        String firstName = lineValues[1];
        String lastName = lineValues[2];
        BigDecimal salary = new BigDecimal(lineValues[3]);
        int managerId = lineValues.length == 5 ? Integer.parseInt(lineValues[4]) : -1; // For CEO, the managerId == -1

        return new Employee(id, firstName, lastName, salary, managerId);
    }
}
