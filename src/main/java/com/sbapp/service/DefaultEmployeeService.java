package com.sbapp.service;

import org.springframework.beans.factory.annotation.Autowired;
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
    private final EmployeeRepository employeeRepository;


    @Autowired
    public DefaultEmployeeService(final EmployeeRepository employeeRepository)
    {
        this.employeeRepository = employeeRepository;
    }


    /**
     * Creates a new Employee.
     *
     * @param employeeDO
     * @return created Employee
     * @throws Exception if some error occurs.
     */
    @Override
    public EmployeeDO create(EmployeeDO employeeDO) throws Exception
    {
    	EmployeeDO employee;
        employee = employeeRepository.create(employeeDO);
        return employee;
    }
    
    /**
     * Updates an Employee against ID.
     * @param employeeId
     * @param employeeDO
     * @throws EntityNotFoundException if no Employee with the given id was found.
     * @throws Exception if some error occurs.
     */
    @Override
    public void update(int employeeId, EmployeeDO employeeDO) throws EntityNotFoundException, Exception
    {
    	employeeDO.setId(employeeId);
    	employeeRepository.update(employeeDO);
    }
    
    /**
     * Deleted an Employee.
     * @param employeeId - ID of en Employee to be deleted
     * @throws EntityNotFoundException if no Employee with the given id was found.
     * @throws Exception if some error occurs.
     */
    @Override
    public void delete(int employeeId) throws EntityNotFoundException, Exception
    {
    	employeeRepository.delete(employeeId);
    }

}
