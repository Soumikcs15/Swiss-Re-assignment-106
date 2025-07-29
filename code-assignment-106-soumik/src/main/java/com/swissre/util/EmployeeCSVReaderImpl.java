package com.swissre.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.swissre.entity.Employee;

public class EmployeeCSVReaderImpl implements EmployeeCSVReader{

	/*
	 * It is assumed that header values can appear dynamically and their order can varies from file to file.
	 * This method is able to create Employee object accordingly
	 * 
	 */
	@Override
    public List<Employee> readEmployees(String csvFilePath) throws IOException {
        List<Employee> employees = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(csvFilePath))) {
            String headerLine = br.readLine();
            if (headerLine == null) {
                throw new IOException("Empty CSV file");
            }

            String[] headers = headerLine.split(",");
            Map<String, Integer> columnIndex = new HashMap<>();
            for (int i = 0; i < headers.length; i++) {
                columnIndex.put(headers[i].trim().toLowerCase(), i);
            }

            String line;
            while ((line = br.readLine()) != null) {
                String[] tokens = line.split(",", -1); // -1 includes trailing empty values

                int id = Integer.parseInt(tokens[columnIndex.get("id")].trim());
                String firstName = tokens[columnIndex.get("firstname")].trim();
                String lastName = tokens[columnIndex.get("lastname")].trim();
                double salary = Double.parseDouble(tokens[columnIndex.get("salary")].trim());

                String managerIdStr = tokens[columnIndex.get("managerid")].trim();
                Integer managerId = managerIdStr.isEmpty() ? null : Integer.parseInt(managerIdStr);

                Employee emp = new Employee(id, firstName, lastName, salary, managerId);
                employees.add(emp);
            }
        }

        return employees;
    }

   
}

