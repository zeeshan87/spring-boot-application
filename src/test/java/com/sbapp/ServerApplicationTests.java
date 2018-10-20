package com.sbapp;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.sbapp.ServerApplication;
import com.sbapp.domainobject.EmployeeDO;
import com.sbapp.service.EmployeeService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ServerApplication.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ServerApplicationTests
{

	private MockMvc mockMvc;
	
	@Autowired
    private WebApplicationContext wac;
	
	// For test initialization by creating some employees
	@Autowired
	EmployeeService employeeService;
	
	// To ensure the body of the setup executes once
	// before all tests instead of before each test
	private static boolean setupCompleted = false;

	@Before
	public void setup() throws Exception {
		
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
		
		if (setupCompleted) {
			return;
		}
 
        // Some more employees to test filterByAge endpoint
        employeeService.create(new EmployeeDO("Test Emp 18", 18, 1500));
        employeeService.create(new EmployeeDO("Test Emp 25", 25, 3000));
        employeeService.create(new EmployeeDO("Test Emp 28", 28, 4000));
        employeeService.create(new EmployeeDO("Test Emp 30", 30, 6000));
        
        setupCompleted = true;
	}
	
	@Test
	public void test01VerifyCreateEmployeePositive() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/employees")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{ \"fullName\": \"Create Test Success\", \"age\": 26,  \"salary\": 1000}")
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isCreated())
		.andExpect(jsonPath("$.id").exists())
		.andExpect(jsonPath("$.fullName").value("Create Test Success"))
		.andExpect(jsonPath("$.age").value(26))
		.andExpect(jsonPath("$.salary").value(1000))
		.andDo(print());
	}
	
	@Test
	public void test02VerifyCreateEmployeeNegative() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/employees")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{ \"fullName\": \"\", \"age\": 15,  \"salary\": 0}")
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isBadRequest())
		.andExpect(jsonPath("$.message").value(containsString("Full Name can not be null or empty")))
		.andExpect(jsonPath("$.message").value(containsString("Age can not be less than 18")))
		.andExpect(jsonPath("$.message").value(containsString("Salary should be greater than zero")))
		.andDo(print());
	}
	
	@Test
	public void test03VerifyUpdateEmployeePositive() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.put("/employees/5")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{ \"fullName\": \"Update Employee Success\", \"age\": 45,  \"salary\": 4500}")
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isNoContent())
		.andDo(print());
	}
	
	@Test
	public void test04VerifyUpdateEmployeeNegative() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.put("/employees/60000")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{ \"fullName\": \"Update Employee Fail\", \"age\": 45,  \"salary\": 4500}")
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isNotFound())
		.andExpect(jsonPath("$.message").value(containsString("Couldn't find an Employee")))
		.andDo(print());
	}
	
	@Test
	public void test05VerifyDeleteEmployeePositive() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.delete("/employees/5")
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isNoContent())
		.andDo(print());
	}
	
	@Test
	public void test06VerifyDeleteEmployeeNegative() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.delete("/employees/5")
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isNotFound())
		.andExpect(jsonPath("$.message").value(containsString("Couldn't find an Employee")))
		.andDo(print());
	}
	
	@Test
	public void test07VerifyGetfilterByAgeLessThanAsc() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/employees/filterByAge")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{ \"operator\": \"lt\", \"value\": 28,  \"sort\": \"asc\"}")
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$", hasSize(2)))
		.andExpect(jsonPath("$.[0].age").value(18))
		.andExpect(jsonPath("$.[1].age").value(25))
		.andDo(print());
	}
	
	@Test
	public void test08VerifyGetfilterByAgeLessThanDesc() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/employees/filterByAge")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{ \"operator\": \"lt\", \"value\": 28,  \"sort\": \"desc\"}")
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$", hasSize(2)))
		.andExpect(jsonPath("$.[0].age").value(25))
		.andExpect(jsonPath("$.[1].age").value(18))
		.andDo(print());
	}
	
	@Test
	public void test08VerifyGetfilterByAgeLessThanEqualsAsc() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/employees/filterByAge")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{ \"operator\": \"lte\", \"value\": 28,  \"sort\": \"asc\"}")
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$", hasSize(3)))
		.andExpect(jsonPath("$.[0].age").value(18))
		.andExpect(jsonPath("$.[1].age").value(25))
		.andExpect(jsonPath("$.[2].age").value(28))
		.andDo(print());
	}
	
	@Test
	public void test09VerifyGetfilterByAgeLessThanEqualsDesc() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/employees/filterByAge")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{ \"operator\": \"lte\", \"value\": 28,  \"sort\": \"desc\"}")
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$", hasSize(3)))
		.andExpect(jsonPath("$.[0].age").value(28))
		.andExpect(jsonPath("$.[1].age").value(25))
		.andExpect(jsonPath("$.[2].age").value(18))
		.andDo(print());
	}
	
	@Test
	public void test10VerifyGetfilterByAgeGreaterThanAsc() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/employees/filterByAge")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{ \"operator\": \"gt\", \"value\": 25,  \"sort\": \"asc\"}")
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$", hasSize(2)))
		.andExpect(jsonPath("$.[0].age").value(28))
		.andExpect(jsonPath("$.[1].age").value(30))
		.andDo(print());
	}
	
	@Test
	public void test11VerifyGetfilterByAgeGreaterThanDesc() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/employees/filterByAge")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{ \"operator\": \"gt\", \"value\": 25,  \"sort\": \"desc\"}")
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$", hasSize(2)))
		.andExpect(jsonPath("$.[0].age").value(30))
		.andExpect(jsonPath("$.[1].age").value(28))
		.andDo(print());
	}
	
	@Test
	public void test12VerifyGetfilterByAgeGreaterThanEqualsAsc() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/employees/filterByAge")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{ \"operator\": \"gte\", \"value\": 25,  \"sort\": \"asc\"}")
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$", hasSize(3)))
		.andExpect(jsonPath("$.[0].age").value(25))
		.andExpect(jsonPath("$.[1].age").value(28))
		.andExpect(jsonPath("$.[2].age").value(30))
		.andDo(print());
	}
	
	@Test
	public void test13VerifyGetfilterByAgeGreaterThanEqualsDesc() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/employees/filterByAge")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{ \"operator\": \"gte\", \"value\": 25,  \"sort\": \"desc\"}")
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$", hasSize(3)))
		.andExpect(jsonPath("$.[0].age").value(30))
		.andExpect(jsonPath("$.[1].age").value(28))
		.andExpect(jsonPath("$.[2].age").value(25))
		.andDo(print());
	}
	
	@Test
	public void test14VerifyGetfilterByAgeEqualsToAsc() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/employees/filterByAge")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{ \"operator\": \"eq\", \"value\": 25,  \"sort\": \"asc\"}")
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$", hasSize(1)))
		.andExpect(jsonPath("$.[0].age").value(25))
		.andDo(print());
	}
	
	@Test
	public void test15VerifyGetfilterByAgeEqualsToDesc() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/employees/filterByAge")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{ \"operator\": \"eq\", \"value\": 25,  \"sort\": \"desc\"}")
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$", hasSize(1)))
		.andExpect(jsonPath("$.[0].age").value(25))
		.andDo(print());
	}
	
	@Test
	public void test15VerifyGetfilterByAgeNotEqualsToAsc() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/employees/filterByAge")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{ \"operator\": \"ne\", \"value\": 25,  \"sort\": \"asc\"}")
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$", hasSize(3)))
		.andExpect(jsonPath("$.[0].age").value(18))
		.andExpect(jsonPath("$.[1].age").value(28))
		.andExpect(jsonPath("$.[2].age").value(30))
		.andDo(print());
	}
	
	@Test
	public void test16VerifyGetfilterByAgeNotEqualsToDesc() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/employees/filterByAge")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{ \"operator\": \"ne\", \"value\": 25,  \"sort\": \"desc\"}")
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$", hasSize(3)))
		.andExpect(jsonPath("$.[0].age").value(30))
		.andExpect(jsonPath("$.[1].age").value(28))
		.andExpect(jsonPath("$.[2].age").value(18))
		.andDo(print());
	}
}
