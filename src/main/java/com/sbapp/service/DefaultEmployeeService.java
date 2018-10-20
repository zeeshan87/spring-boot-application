package com.sbapp.service;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.sbapp.dataaccessobject.EmployeeRepository;
import com.sbapp.domainobject.EmployeeDO;
import com.sbapp.exception.EntityNotFoundException;

/**
 * Service to encapsulate the link between DAO and controller and to have business logic for some employee specific things.
 * <p/>
 */
@Service
public class DefaultEmployeeService implements EmployeeService
{

    private static final Logger LOG = LoggerFactory.getLogger(DefaultEmployeeService.class);

    private final EmployeeRepository employeeRepository;


    public DefaultEmployeeService(final EmployeeRepository employeeRepository)
    {
        this.employeeRepository = employeeRepository;
    }


    /**
     * Creates a new Employee.
     *
     * @param employeeDO
     * @return created Employee
     */
    @Override
    public EmployeeDO create(EmployeeDO employeeDO) throws Exception
    {
    	EmployeeDO employee;
        try
        {
        	employee = employeeRepository.create(employeeDO);
        }
        catch (Exception e)
        {
            LOG.warn("Exception while creating an employee: {}", employeeDO, e);
            throw e;
        }
        return employee;
    }

}
