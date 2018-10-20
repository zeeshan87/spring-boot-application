package com.sbapp.datatransferobject;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(value="EmployeeDTO", description="Employee Model for the API documentation")
public class EmployeeDTO
{
	@ApiModelProperty(notes = "ID of the Employee", required = false, readOnly = true)
    private int id;

    @NotNull(message = "Full Name can not be null!")
    @ApiModelProperty(notes = "Name of the Employee", required = true)
    private String fullName;

    @NotNull(message = "Age can not be null!")
    @ApiModelProperty(notes = "Age of the Employee", required = true)
    private int age;
    
    @NotNull(message = "Salary can not be null!")
    @ApiModelProperty(notes = "Salary of the Employee", required = true)
    private int salary;

    private EmployeeDTO()
    {
    }


    public EmployeeDTO(int id, String fullName, int age, int salary)
    {
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
