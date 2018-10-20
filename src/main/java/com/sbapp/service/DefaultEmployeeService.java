package com.sbapp.service;

import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sbapp.dataaccessobject.EmployeeRepository;
import com.sbapp.domainobject.EmployeeDO;
import com.sbapp.domainvalue.Operator;
import com.sbapp.domainvalue.Sort;
import com.sbapp.exception.EntityNotFoundException;
import com.sbapp.util.EmployeeComparators;
import com.sbapp.util.EmployeePredicates;

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
     * @param employeeId - ID of an Employee to be deleted
     * @throws EntityNotFoundException if no Employee with the given id was found.
     * @throws Exception if some error occurs.
     */
    @Override
    public void delete(int employeeId) throws EntityNotFoundException, Exception
    {
    	employeeRepository.delete(employeeId);
    }


    /**
     * Find and sort employees by age.
     * @param operator - Operator to be used in filter
     * @param age - Age to be compared
     * @param sort - Either in ascending or descending order
     * @return Employyes list
     */
	@Override
	public List<EmployeeDO> findByAge(Operator operator, int age, Sort sort) {
		Predicate<EmployeeDO> predicate = null;
		
		switch(operator) {
		case eq:
			predicate = EmployeePredicates.isAgeEqualTo(age);
			break;
		case gt:
			predicate = EmployeePredicates.isAgeGreaterThan(age);
			break;
		case gte:
			// Negation of LessThan is GreaterThanEqualsTo
			predicate = EmployeePredicates.isAgeLessThan(age).negate();
			break;
		case lt:
			predicate = EmployeePredicates.isAgeLessThan(age);
			break;
		case lte:
			// Negation of GreatersThan is LessThanEqualsTo
			predicate = EmployeePredicates.isAgeGreaterThan(age).negate();
			break;
		case ne:
			predicate = EmployeePredicates.isAgeEqualTo(age).negate();
			break;		
		}
		
		Comparator<EmployeeDO> comparator = null;
		
		switch(sort) {
		case asc:
			comparator = EmployeeComparators.AGE_ASC_COMPARATOR;
			break;
		case desc:
			comparator = EmployeeComparators.AGE_ASC_COMPARATOR.reversed();
			break;
		}
		
		return employeeRepository.find(predicate, comparator);
	}

}
