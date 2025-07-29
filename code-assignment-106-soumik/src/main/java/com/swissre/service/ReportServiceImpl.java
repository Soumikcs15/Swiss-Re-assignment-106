package com.swissre.service;

import static com.swissre.constants.Constants.MGR_WITH_HIGHER_AVG_SAL;
import static com.swissre.constants.Constants.MGR_WITH_LESS_AVG_SAL;
import static com.swissre.constants.Constants.NO_EMP_WITH_HIGHER_HIERARCHY_MSG;
import static com.swissre.constants.Constants.NO_EMP_WITH_HIGHER_SAL_MSG;
import static com.swissre.constants.Constants.NO_EMP_WITH_LESS_SAL_MSG;
import static com.swissre.constants.Constants.NO_REPORT_MSG;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.swissre.entity.Employee;

public class ReportServiceImpl implements ReportService {

	@Override
	public void showSalaryReport(Map<String, Map<Employee, Double>> empSalaryMap) {
		if(empSalaryMap == null || empSalaryMap.isEmpty()){
			System.out.println(NO_REPORT_MSG);
		}else{
			System.out.println("=====================================");
			System.out.println("Employees Earning Less Salary Report");
			System.out.println("=====================================");
			Map<Employee, Double> lessAvgSalEmps = empSalaryMap.get(MGR_WITH_LESS_AVG_SAL);
			if(lessAvgSalEmps == null || lessAvgSalEmps.isEmpty()){
				System.out.println(NO_EMP_WITH_LESS_SAL_MSG);
			}else{
				System.out.printf("%-8s %-30s %-15s %-15s"," ID","Employee Name","Salary","Less By\n");
				System.out.println("--------------------------------------------------------------");
				lessAvgSalEmps.entrySet().forEach(emp-> System.out.printf("%-8d %-30s $%-15.2f $%-15.2f \n",emp.getKey().getId(),
						emp.getKey().getFirstName()+" "+emp.getKey().getLastName(), emp.getKey().getSalary(),emp.getValue()));
			}
			
			System.out.println("=====================================");
			System.out.println("Employees Earning Higher Salary Report");
			System.out.println("=====================================");
			Map<Employee, Double> higherAvgSalEmps = empSalaryMap.get(MGR_WITH_HIGHER_AVG_SAL);
			if(higherAvgSalEmps == null || higherAvgSalEmps.isEmpty()){
				System.out.println(NO_EMP_WITH_HIGHER_SAL_MSG);
			}else{
				System.out.printf("%-8s %-30s %-15s %-15s"," ID","Employee Name","Salary","More By\n");
				System.out.println("--------------------------------------------------------------");
				higherAvgSalEmps.entrySet().forEach(emp-> System.out.printf("%-8d %-30s $%-15.2f $%-15.2f \n",emp.getKey().getId(),
						emp.getKey().getFirstName()+" "+emp.getKey().getLastName(), emp.getKey().getSalary(),emp.getValue()));
			}
				
		}

	}

	@Override
	public void printSalaryReportAsCsv(Map<String, Map<Employee, Double>> empSalaryMap, String filePath) {
		if (empSalaryMap == null || empSalaryMap.isEmpty()) {
	        System.out.println("No salary report to export.");
	        return;
	    }

	    try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {

	        // Write Employees Earning Less Salary
	        writer.write("Employees Earning Less Salary Report\n");
	        writer.write("ID,Employee Name,Salary,Less By\n");

	        Map<Employee, Double> lessAvgSalEmps = empSalaryMap.get(MGR_WITH_LESS_AVG_SAL);
	        if (lessAvgSalEmps == null || lessAvgSalEmps.isEmpty()) {
	            writer.write("No employees earning less salary\n");
	        } else {
	            for (Map.Entry<Employee, Double> entry : lessAvgSalEmps.entrySet()) {
	                Employee emp = entry.getKey();
	                writer.write(String.format("%d,%s, $%.2f,$%.2f\n",
	                        emp.getId(),
	                        emp.getFirstName() + " " + emp.getLastName(),
	                        emp.getSalary(),
	                        entry.getValue()));
	            }
	        }

	        writer.newLine();

	        // Write Employees Earning Higher Salary
	        writer.write("Employees Earning Higher Salary Report\n");
	        writer.write("ID,Employee Name,Salary,More By\n");

	        Map<Employee, Double> higherAvgSalEmps = empSalaryMap.get(MGR_WITH_HIGHER_AVG_SAL);
	        if (higherAvgSalEmps == null || higherAvgSalEmps.isEmpty()) {
	            writer.write("No employees earning higher salary\n");
	        } else {
	            for (Map.Entry<Employee, Double> entry : higherAvgSalEmps.entrySet()) {
	                Employee emp = entry.getKey();
	                writer.write(String.format("%d,%s, $%.2f,$%.2f\n",
	                        emp.getId(),
	                        emp.getFirstName() + " " + emp.getLastName(),
	                        emp.getSalary(),
	                        entry.getValue()));
	            }
	        }

	        System.out.println("Salary report successfully written to: " + filePath);

	    } catch (IOException e) {
	        System.err.println("Error writing salary report to CSV: " + e.getMessage());
	    }
	}
	

	@Override
	public void showHierarchyReport(List<Employee> empWithHigerHierarchy) {
		System.out.println("============================================");
		System.out.println("Employees With Higer Hierarcy Chain Report");
		System.out.println("============================================");
		
		if(empWithHigerHierarchy == null || empWithHigerHierarchy.isEmpty()){
			System.out.println(NO_EMP_WITH_HIGHER_HIERARCHY_MSG);
		}else{
			System.out.printf("%-8s %-30s %-10s %-10s"," ID","Employee Name","Hierarchy Level","More By\n");
			System.out.println("------------------------------------------------------------------------------");
			empWithHigerHierarchy.forEach(emp-> System.out.printf("%-8d %-30s %-10d %-10d \n",emp.getId(),
					emp.getFirstName()+" "+emp.getLastName(), emp.getLevel(),emp.getLevel()-4));
		}
		

	}

	@Override
	public void printHierarchyReportAsCsv(List<Employee> empWithHigerHierarchy, String filePath) {
		
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {

	        writer.write("Employees With Higher Hierarchy Chain Report\n");

	        if (empWithHigerHierarchy == null || empWithHigerHierarchy.isEmpty()) {
	            writer.write("No employees with higher hierarchy chain\n");
	        } else {
	            // CSV Header
	            writer.write("ID,Employee Name,Hierarchy Level,More By\n");

	            for (Employee emp : empWithHigerHierarchy) {
	                int moreBy = emp.getLevel() - 4; // Assuming base level is 4
	                writer.write(String.format("%d,%s,%d,%d\n",
	                        emp.getId(),
	                        emp.getFirstName() + " " + emp.getLastName(),
	                        emp.getLevel(),
	                        moreBy));
	            }
	        }

	        System.out.println("Hierarchy report successfully written to: " + filePath);

	    } catch (IOException e) {
	        System.err.println("Error writing hierarchy report to CSV: " + e.getMessage());
	    }

	}

}
