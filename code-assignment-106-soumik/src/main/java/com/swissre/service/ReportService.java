package com.swissre.service;

import java.util.List;
import java.util.Map;

import com.swissre.entity.Employee;

public interface ReportService {

	public void showSalaryReport(Map<String, Map<Employee, Double>> empSalaryMap);
	public void printSalaryReportAsCsv(Map<String, Map<Employee, Double>> empSalaryMap, String filePath);
	
	public void showHierarchyReport(List<Employee> empWithHigerHierarchy);
	public void printHierarchyReportAsCsv(List<Employee> empWithHigerHierarchy, String filePath);
	
}
