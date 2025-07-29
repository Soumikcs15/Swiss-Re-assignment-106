package com.swissre.dto;

import java.util.LinkedList;
import java.util.List;

import com.swissre.entity.Employee;

public class Subordinates {
	private double totalSubordinatesSalary = 0.0;
	private List<Employee> subordinates = null;
	
	public Subordinates(){
		subordinates = new LinkedList<>();
	}
	
	public void addSubordinate(Employee emp){
		subordinates.add(emp);
		totalSubordinatesSalary += emp.getSalary();
	}

	public double getTotalSubordinatesSalary() {
		return totalSubordinatesSalary;
	}

	public List<Employee> getSubordinates() {
		return subordinates;
	}
	
	public int getSubordinatesCount(){
		return subordinates != null ? subordinates.size() : 0;
	}

	@Override
	public String toString() {
		return "Subordinates [totalSubordinatesSalary=" + totalSubordinatesSalary + ", subordinates=" + subordinates
				+ "]";
	}

	
	

}
