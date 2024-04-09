package com.uzkikh.parser;

import com.uzkikh.exception.CsvParserException;
import com.uzkikh.model.Employee;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CsvFileParserImplTest {

    private static CsvFileParserImpl csvFileParser;

    @BeforeAll
    static void beforeAll() {
        csvFileParser = new CsvFileParserImpl();
    }

    @Test
    void testShouldThrowExceptionIfFilenameIsNull() {
        assertThrows(CsvParserException.class, () -> csvFileParser.parseCsv(null));
    }

    @Test
    void testShouldThrowExceptionIfFileNotFound() {
        RuntimeException ex = assertThrows(CsvParserException.class, () -> csvFileParser.parseCsv("abcde"));

        assertEquals(FileNotFoundException.class, ex.getCause().getClass());
    }

    @Test
    void testShouldThrowExceptionIfColumnIdFormatIsInvalid() {
        assertThrows(NumberFormatException.class,
                () -> csvFileParser.parseCsv("src/test/resources/invalid_id_column.csv"));
    }

    @Test
    void testShouldThrowExceptionIfColumnManagerIdFormatIsInvalid() {
        assertThrows(NumberFormatException.class,
                () -> csvFileParser.parseCsv("src/test/resources/invalid_manager_id_column.csv"));
    }

    @Test
    void testShouldThrowExceptionIfCsvFileDoesNotContainRequiredFieldsInLine() {
        assertThrows(CsvParserException.class,
                () -> csvFileParser.parseCsv("src/test/resources/invalid_line.csv"));
    }

    @Test
    void testShouldReturnEmployeeList() {
        List<Employee> expected = List.of(
                new Employee(123, "Joe", "Doe", new BigDecimal(60000), -1),
                new Employee(124, "Martin", "Chekov", new BigDecimal(45000), 123)
        );

        List<Employee> employeeList = csvFileParser.parseCsv("src/test/resources/data.csv");

        assertEquals(2, employeeList.size());
        assertTrue(() -> employeeList.containsAll(expected));
    }
}