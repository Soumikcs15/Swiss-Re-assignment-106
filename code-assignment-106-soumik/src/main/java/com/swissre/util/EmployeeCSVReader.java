package com.swissre.util;

import java.io.IOException;
import java.util.List;

import com.swissre.entity.Employee;

public interface EmployeeCSVReader {

	public List<Employee> readEmployees(String csvFilePath) throws IOException;
}
