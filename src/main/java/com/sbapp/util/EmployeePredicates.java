package com.sbapp.util;

import java.util.function.Predicate;

import com.sbapp.domainobject.EmployeeDO;

public class EmployeePredicates {
	
	/**
     * This method returns a predicate for an employee's age 
     * less than provided age.
     * @param age - age to compare
     * @return Predicate<Employee>
     */
    public static Predicate<EmployeeDO> isAgeLessThan(int age) {
        return employee -> employee.getAge() < age;
    }
    
	/**
     * This method returns a predicate for an employee's age 
     * greater than provided age.
     * @param age - age to compare
     * @return Predicate<Employee>
     */
    public static Predicate<EmployeeDO> isAgeGreaterThan(int age) {
        return employee -> employee.getAge() > age;
    }
    
	/**
     * This method returns a predicate for an employee's age 
     * equal to provided age.
     * @param age - age to compare
     * @return Predicate<Employee>
     */
    public static Predicate<EmployeeDO> isAgeEqualTo(int age) {
        return employee -> employee.getAge() == age;
    }
}