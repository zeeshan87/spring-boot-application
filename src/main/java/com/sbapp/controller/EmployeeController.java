package com.sbapp.controller;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.sbapp.controller.mapper.EmployeeMapper;
import com.sbapp.datatransferobject.EmployeeDTO;
import com.sbapp.domainobject.EmployeeDO;
import com.sbapp.exception.EntityNotFoundException;
import com.sbapp.service.EmployeeService;

import io.swagger.annotations.ApiOperation;

/**
 * All operations with a driver will be routed by this controller.
 */
@RestController
@RequestMapping("employees")
public class EmployeeController
{

    private final EmployeeService employeeService;


    @Autowired
    public EmployeeController(final EmployeeService employeeService)
    {
        this.employeeService = employeeService;
    }


    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Create a new Employee",
    notes = "Needs to provide non empty FullName, Age <= 18 and non-zero Salary",
    response = EmployeeDTO.class)
    public EmployeeDTO createEmployee(@Valid @RequestBody EmployeeDTO employeeDTO) throws Exception
    {
        EmployeeDO employeeDO = EmployeeMapper.makeEmployeeDO(employeeDTO);
        return EmployeeMapper.makeEmployeeDTO(employeeService.create(employeeDO));
    }
    
    
    @PutMapping(path ="/{employeeId}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Updates an Employee",
    notes = "Needs to provide non empty FullName, Age <= 18 and non-zero Salary")
    public void updateEmployee(
        @PathVariable int employeeId, @Valid @RequestBody EmployeeDTO employeeDTO)
        throws EntityNotFoundException, Exception
    {
    	EmployeeDO employeeDO = EmployeeMapper.makeEmployeeDO(employeeDTO);
    	employeeService.update(employeeId, employeeDO);
    }
}
