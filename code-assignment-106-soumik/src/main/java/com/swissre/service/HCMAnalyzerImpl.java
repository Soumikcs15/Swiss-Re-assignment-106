package com.swissre.service;

import static com.swissre.constants.ConfigConstants.MAX_HIERARCY_LEVEL;
import static com.swissre.constants.Constants.MGR_WITH_HIGHER_AVG_SAL;
import static com.swissre.constants.Constants.MGR_WITH_LESS_AVG_SAL;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.swissre.dto.Subordinates;
import com.swissre.entity.Employee;
import com.swissre.util.EmployeeCSVReader;
import com.swissre.util.EmployeeCSVReaderImpl;

public class HCMAnalyzerImpl implements HCMAnalyzer {

	private EmployeeCSVReader empFileReader = new EmployeeCSVReaderImpl();
	private ReportService reportService = new ReportServiceImpl();
	
	@Override
	public void analyzeHCMData(String filePath) {
		Map<Integer, Subordinates> mgrSubrdnateMap = new HashMap<>();
		Map<Integer, Employee> managerMap = new HashMap<>();
		try {
			List<Employee> empList = empFileReader.readEmployees(filePath);
			// Adding all the direct subordinates under manager IDs
			for (Employee emp : empList) {
				Integer managerId = emp.getManagerId();
				if (managerId != null) {
					if (mgrSubrdnateMap.containsKey(managerId)) {
						mgrSubrdnateMap.get(emp.getManagerId()).addSubordinate(emp);
					} else {
						Subordinates subordinates = new Subordinates();
						subordinates.addSubordinate(emp);
						mgrSubrdnateMap.put(managerId, subordinates);
					}

					// Adding manager details
					managerMap.put(managerId, null);
				}
			}
			// Updating manager details
			for (Employee emp : empList) {
				if (managerMap.containsKey(emp.getId())) {
					managerMap.put(emp.getId(), emp);
				}
			}

//			Map<String, Map<Employee, String>> analyzeSalary = analyzeSalary(managerMap, mgrSubrdnateMap);
			Map<String, Map<Employee, Double>> empSalaryMap = analyzeSalary_v2(managerMap, mgrSubrdnateMap);
			List<Employee> empWithHigerHierarchy = calculateAndUpdateEmpHierarchy(managerMap, mgrSubrdnateMap);
			
			reportService.showSalaryReport(empSalaryMap);
			//reportService.printSalaryReportAsCsv(empSalaryMap, "C:\\Users\\Soumik PC\\Desktop\\SwissRe Coding Round\\employees_100_salary_report.csv");
			reportService.showHierarchyReport(empWithHigerHierarchy);
			//reportService.printHierarchyReportAsCsv(empWithHigerHierarchy,"C:\\Users\\Soumik PC\\Desktop\\SwissRe Coding Round\\employees_100_hierarchy_report.csv");
			//showReport(analyzeSalary);

		} catch (IOException e) {

			e.printStackTrace();
		}

	}

	private List<Employee> calculateAndUpdateEmpHierarchy(Map<Integer, Employee> managerMap,
			Map<Integer, Subordinates> mgrSubrdnateMap) {
		List<Employee> empWithHigerHierarchy = new ArrayList<>();
		for(Entry<Integer, Subordinates>mgrSubordinate : mgrSubrdnateMap.entrySet()){
			Integer mgrId = mgrSubordinate.getKey();
			int empLevel = 0;
			empLevel = calculteEmployeeLevel(mgrId,managerMap,empLevel);
			//Updating all subordinate levels
			Subordinates subordinat = mgrSubordinate.getValue();
			for(Employee emp : subordinat.getSubordinates()){
				emp.setLevel(empLevel);
				if(empLevel>MAX_HIERARCY_LEVEL){
					empWithHigerHierarchy.add(emp);
				}
			}
		}

		return empWithHigerHierarchy;
	}

	private int calculteEmployeeLevel(Integer mgrId, Map<Integer, Employee> managerMap, int empLevel) {
		if(mgrId == null){//Reached at CEO level
			return empLevel;
		}else{
			Employee nextLevelMgr = managerMap.get(mgrId);
			if(nextLevelMgr.getLevel() != -1){
				empLevel = nextLevelMgr.getLevel() + 1;
			}else{
				empLevel += 1;
				empLevel = calculteEmployeeLevel(nextLevelMgr.getManagerId(),managerMap,empLevel);
			}
			return empLevel;
		}
		
	}

	public Map<String, Map<Employee, Double>> analyzeSalary_v2(Map<Integer, Employee> managerMap,
			Map<Integer, Subordinates> mgrSubrdnateMap) {
		Map<String, Map<Employee, Double>> salaryMap = new HashMap<>();
		Map<Employee, Double> managerWithLessSalary = new HashMap<>();
		Map<Employee, Double> managerWithHighSalary = new HashMap<>();
		double expectedMinSal = 0;
		double expectedMaxSal = 0;
		for (Entry<Integer, Employee> mgrEntry : managerMap.entrySet()) {
			Employee mgr = mgrEntry.getValue();
			Subordinates subordinates = mgrSubrdnateMap.get(mgrEntry.getKey());
			// Calculating average salary
			double totalSubordinatesSalary = subordinates.getTotalSubordinatesSalary();
			double avgSal = 0.0;
			if (totalSubordinatesSalary > 0) {
				avgSal = totalSubordinatesSalary / subordinates.getSubordinatesCount();
				expectedMinSal = avgSal * 1.2;
				expectedMaxSal = avgSal * 1.5;
				if (mgr.getSalary() < expectedMinSal) {
					managerWithLessSalary.put(mgr,  (expectedMinSal - mgr.getSalary()));
				} else if (mgr.getSalary() > expectedMaxSal) {
					managerWithHighSalary.put(mgr, (mgr.getSalary() - expectedMaxSal));
				}
			}
		}

		salaryMap.put(MGR_WITH_LESS_AVG_SAL, managerWithLessSalary);
		salaryMap.put(MGR_WITH_HIGHER_AVG_SAL, managerWithHighSalary);

		return salaryMap;
	}

}
