# Employee Analyzer Tool

This Java program is created to analyze the organizational structure of a company based on the provided CSV file containing information about employees. The program identifies potential improvements according to the specified requirements:

- Ensuring that every manager earns at least 20% more than the average salary of its direct subordinates, but no more than 50% more than that average.
- Identifying employees with reporting lines that exceed 4 managers between them and the CEO.

## Requirements
- Java version 21
- CSV file containing information about employees in the following format:
```csv
Id,firstName,lastName,salary,managerId
123,Joe,Doe,60000,
124,Martin,Chekov,45000,123
125,Bob,Ronstad,47000,123
300,Alice,Hasacat,50000,124
305,Brett,Hardleaf,34000,300 
```
### Description of CSV file
- **Id**: Unique identifier for each employee.
- **firstName**: First name of the employee.
- **lastName**: Last name of the employee.
- **salary**: Salary of the employee.
- **managerId**: Id of the manager of the employee. The CEO should have no manager specified.
> **Important note:** Column separators in the csv file must be the "," characters.


## Usage
- Ensure you have Java required version installed on your system.
- Prepare a CSV file containing employee information in the above format.
- Clone the source code from the repository.
```bash
git clone git@github.com:uzkikh/employee-analyzer-tool.git
```
- Compile the program using the provided source code.
```bash
mvn clean package
```
- Run the program and provide the path to the CSV file as a command-line argument.
```bash
java -jar target/employee-analyzer-1.0-SNAPSHOT-jar-with-dependencies.jar path-to-your-csv-source-file
```

## Output example
The program generates output like this:
```bash
File "/Users/ivan/temp/data.csv" processed successfully. Number of records in the file = 20

Results of the analysis of the company structure and current salaries:
Employee{id=124, firstName='Martin', lastName='Chekov', salary=45000, managerId=123} salary must be adjusted by 15000.00
Employee{id=305, firstName='Brett', lastName='Hardleaf', salary=34000, managerId=300} has reporting length=6, longer than required by 2

...
```