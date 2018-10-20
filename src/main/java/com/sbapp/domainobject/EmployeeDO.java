package com.sbapp.domainobject;

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
    
}
