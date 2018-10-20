package com.sbapp.service;

import com.sbapp.domainobject.EmployeeDO;
import com.sbapp.exception.EntityNotFoundException;

/*
 * Service interface for Employee
 */
public interface EmployeeService
{
    EmployeeDO create(EmployeeDO employeeDO) throws Exception;
    void update(int employeeId, EmployeeDO employeeDO) throws EntityNotFoundException, Exception;
    void delete(int employeeId) throws EntityNotFoundException, Exception;
}
