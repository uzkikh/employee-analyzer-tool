package com.uzkikh.parser;

import com.uzkikh.model.Employee;

import java.util.List;

public interface CsvFileParser {

    List<Employee> parseCsv(String fileName);
}
