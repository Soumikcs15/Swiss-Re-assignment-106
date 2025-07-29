package com.swissre.service;

import java.util.List;
import java.util.Map;

import com.swissre.entity.Employee;
import static com.swissre.constants.Constants.*;

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
		// TODO Auto-generated method stub

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
		
			

	}

}
