package com.sbapp.controller.mapper;

import java.util.List;
import java.util.stream.Collectors;

import com.sbapp.datatransferobject.EmployeeDTO;
import com.sbapp.domainobject.EmployeeDO;

/*
 * Mapper class to transform Domain Objects to Data Transfer Objects and vice versa
 */
public class EmployeeMapper
{
    public static EmployeeDO makeEmployeeDO(EmployeeDTO employeeDTO)
    {
        return new EmployeeDO(employeeDTO.getFullName(), employeeDTO.getAge(), 
        		employeeDTO.getSalary());
    }


    public static EmployeeDTO makeEmployeeDTO(EmployeeDO employeeDO)
    {
    	return new EmployeeDTO(employeeDO.getId(), employeeDO.getFullName(),
    			employeeDO.getAge(), employeeDO.getSalary());
    }


    public static List<EmployeeDTO> makeEmployeeDTOList(List<EmployeeDO> employees)
    {
        return employees.stream()
            .map(EmployeeMapper::makeEmployeeDTO)
            .collect(Collectors.toList());
    }
}
