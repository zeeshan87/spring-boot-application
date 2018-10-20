package com.sbapp.dataaccessobject;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.sbapp.config.ApplicationConstants;
import com.sbapp.domainobject.EmployeeDO;
import com.sbapp.exception.EntityNotFoundException;
import com.sbapp.util.Helper;

/**
 * Database Access Object for employee table.
 * (Will use json file instead of DB table)
 */
@Component
public class EmployeeRepository {
    private static final List<EmployeeDO> employees;

    static {
        try (Reader reader = 
        		new FileReader(Helper.createFileWithDirectory(
        				ApplicationConstants.FILE_DIR, 
        				ApplicationConstants.FILENAME, "[]"))
        	) {
            Type type = new TypeToken<List<EmployeeDO>>() {}.getType();
            employees = new Gson().fromJson(reader, type);
        } 
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    
    /**
     * Creates a new Employee.
     *
     * @param employeeDO
     * @return created Employee
     * @throws Exception if some error occurs.
     */
    public EmployeeDO create(EmployeeDO employeeDO) throws Exception {
    	// To save the current state of the list in case save to json file fails
    	EmployeeDO[] currentState = employees.toArray(new EmployeeDO[0]);
    	
    	try {
    		int id;
        	int size = employees.size();
        	if (size == 0) {
        		id = 1;
        	}
        	else {
        		id = employees.get(size - 1).getId() + 1; 
        	}   	
        	employeeDO.setId(id);    	
        	employees.add(employeeDO);
        	save();
    	}
    	catch(IOException ex) {
    		// Reset the list to previous state (Transactional Workaround)
    		employees.clear();
    		employees.addAll(Arrays.asList(currentState));
    		throw ex;
    	}
    	
    	
    	return employeeDO;
    }
    
    /**
     * Updates an Employee.
     *
     * @param employeeDO
     * @return created Employee
     * @throws EntityNotFoundException if no Employee with the given id was found.
     * @throws Exception if some error occurs.
     */
    public void update(EmployeeDO employeeDO) throws EntityNotFoundException, Exception {
    	// Index of works because it's comparing based 
    	// on ID via overriden equalsTo method in EmployeeDO 
    	int index = employees.indexOf(employeeDO);

    	if (index == -1) {
    		throw new EntityNotFoundException("Couldn't find an Employee with ID: " + employeeDO.getId());
    	}
    	else {
    		// To save the current state of the list in case save to json file fails
        	EmployeeDO[] currentState = employees.toArray(new EmployeeDO[0]);        	
        	try {
        		employees.set(index, employeeDO);
        		save();
        	}
        	catch(IOException ex) {
        		// Reset the list to previous state (Transactional Workaround)
        		employees.clear();
        		employees.addAll(Arrays.asList(currentState));
        		throw ex;
        	}
    	}
    }
    
    /**
     * Deletes an Employee.
     *
     * @param employeeId - ID of an Employee
     * @throws EntityNotFoundException if no Employee with the given id was found.
     * @throws Exception if some error occurs.
     */
    public void delete(int employeeId) throws EntityNotFoundException, Exception {
    	// To save the current state of the list in case save to json file fails
    	EmployeeDO[] currentState = employees.toArray(new EmployeeDO[0]);
    	
    	try {
    		// Place holder object with just ID because of overriden equalsTo method
        	EmployeeDO employeeDO = new EmployeeDO(employeeId, "", 0, 0);
        	
        	// Remove works because it's comparing based 
        	// on ID via overriden equalsTo method in EmployeeDO 
        	boolean deleted = employees.remove(employeeDO);

        	if (deleted) {
        		save();    		
        	}
        	else {
        		throw new EntityNotFoundException("Couldn't find an Employee with ID: " + employeeId);
        	}
    	}
    	catch(IOException ex) {
    		// Reset the list to previous state (Transactional Workaround)
    		employees.clear();
    		employees.addAll(Arrays.asList(currentState));
    		throw ex;
    	}    	
    }
    
    /**
     * Find employees.
     *
     * @param predicate - Predicate to filter employees
     * @param comparator - Comparator to sort employees
     * @return List of employees
     */
    public List<EmployeeDO> find(Predicate<EmployeeDO>  predicate, Comparator<EmployeeDO> comparator) {
    	return employees.stream()
    			.filter(predicate)
    			.sorted(comparator)
    			.collect(Collectors.toList());
    }

    /**
     * Saves Employees list to JSON file
     */
	private void save() throws IOException {		
		try (Writer writer = new FileWriter(ApplicationConstants.FILE_DIR + "/" + ApplicationConstants.FILENAME)) {
		    Gson gson = new GsonBuilder().setPrettyPrinting().create();
		    Type type = new TypeToken<List<EmployeeDO>>() {}.getType();
		    gson.toJson(employees, type, writer);
		}
	}
}