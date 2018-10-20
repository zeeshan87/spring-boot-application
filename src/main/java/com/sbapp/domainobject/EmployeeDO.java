package com.sbapp.domainobject;

import java.util.Objects;

public class EmployeeDO
{

    private int id;
    private String fullName;
    private int age;
    private int salary;


    private EmployeeDO()
    {
    }


    public EmployeeDO(String fullName, int age, int salary) {
		this.fullName = fullName;
		this.age = age;
		this.salary = salary;
	}
    
    public EmployeeDO(int id, String fullName, int age, int salary) {
    	this.id = id;
		this.fullName = fullName;
		this.age = age;
		this.salary = salary;
	}


	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}


	public String getFullName() {
		return fullName;
	}


	public void setFullName(String fullName) {
		this.fullName = fullName;
	}


	public int getAge() {
		return age;
	}


	public void setAge(int age) {
		this.age = age;
	}


	public int getSalary() {
		return salary;
	}


	public void setSalary(int salary) {
		this.salary = salary;
	}
	
	/*
	 * Method to compare strings based on id
	 * for better searching (could be considered as a 
	 * short way to find the index of the element in list)
	 * CAUTION: I used it for the given purpose and as such
	 * this particular implementation couldn't be appropriate for all cases.
	 */
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmployeeDO employee = (EmployeeDO) o;
        return Objects.equals(id, employee.id);
    }
    
}
