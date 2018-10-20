package com.sbapp.service;

import com.sbapp.domainobject.EmployeeDO;

/*
 * Service interface for Employee
 */
public interface EmployeeService
{
    EmployeeDO create(EmployeeDO employeeDO) throws Exception;

}
