package com.sbapp.dataaccessobject;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;
import java.util.List;

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
        		Helper.createFileWithDirectory(ApplicationConstants.FILE_DIR, 
        				ApplicationConstants.FILENAME, "[]")) {
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
     */
    public EmployeeDO create(EmployeeDO employeeDO) throws Exception {
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
    	
    	return employeeDO;
    }
    
    /**
     * Updates an Employee.
     *
     * @param employeeDO
     * @return created Employee
     */
    public void update(EmployeeDO employeeDO) throws EntityNotFoundException, Exception {
    	// Index of works because it's comparing based 
    	// on ID via overriden equalsTo method in EmployeeDO 
    	int index = employees.indexOf(employeeDO);

    	if (index == -1) {
    		throw new EntityNotFoundException("Couldn't find an Employee with ID: " + employeeDO.getId());
    	}
    	else {
    		employees.set(index, employeeDO);
    		save();
    	}
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