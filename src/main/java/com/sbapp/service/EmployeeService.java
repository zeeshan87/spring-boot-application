package com.sbapp.service;

import java.util.List;

import com.sbapp.domainobject.EmployeeDO;
import com.sbapp.domainvalue.Operator;
import com.sbapp.domainvalue.Sort;
import com.sbapp.exception.EntityNotFoundException;

/*
 * Service interface for Employee
 */
public interface EmployeeService
{
    EmployeeDO create(EmployeeDO employeeDO) throws Exception;
    void update(int employeeId, EmployeeDO employeeDO) throws EntityNotFoundException, Exception;
    void delete(int employeeId) throws EntityNotFoundException, Exception;
    List<EmployeeDO> findByAge(Operator operator, int age, Sort sort);
}
