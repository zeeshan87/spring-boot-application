package com.sbapp.controller;

import java.util.List;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.sbapp.controller.mapper.EmployeeMapper;
import com.sbapp.datatransferobject.AgeFilterDTO;
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
    public EmployeeController(final EmployeeService employeeService) {
        this.employeeService = employeeService;
    }


    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Create a new Employee",
    notes = "Needs to provide non empty FullName, Age <= 18 and non-zero Salary",
    response = EmployeeDTO.class)
    public EmployeeDTO createEmployee(@Valid @RequestBody EmployeeDTO employeeDTO) throws Exception {
        EmployeeDO employeeDO = EmployeeMapper.makeEmployeeDO(employeeDTO);
        return EmployeeMapper.makeEmployeeDTO(employeeService.create(employeeDO));
    }
    
    
    @PutMapping(path ="/{employeeId}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Updates an Employee",
    notes = "Needs to provide non empty FullName, Age <= 18 and positive non-zero Salary")
    public void updateEmployee(
        @PathVariable int employeeId, @Valid @RequestBody EmployeeDTO employeeDTO)
        throws EntityNotFoundException, Exception {
    	EmployeeDO employeeDO = EmployeeMapper.makeEmployeeDO(employeeDTO);
    	employeeService.update(employeeId, employeeDO);
    }
    
    @DeleteMapping(path ="/{employeeId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Deletes an Employee",
    notes = "Provide valid ID for employee to be deleted")
    public void deleteEmployee(
        @PathVariable int employeeId)
        throws EntityNotFoundException, Exception {
    	employeeService.delete(employeeId);
    }
    
    // Post mapping to accept the request body
    // Another workaround would be to use RequestParams with GetMapping
    @PostMapping(path ="/filterByAge", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Get a list of employees filtered and sorted by age",
    notes = "Provide valid values of operator, value and sort as described in the model",
    response = EmployeeDTO.class,
    responseContainer = "List")
    public List<EmployeeDTO> filterByAge(@Valid @RequestBody AgeFilterDTO filterDTO) {
        return EmployeeMapper
        		.makeEmployeeDTOList(employeeService.findByAge(filterDTO.getOperator(),
        				filterDTO.getValue(), filterDTO.getSort()));
    }
}
