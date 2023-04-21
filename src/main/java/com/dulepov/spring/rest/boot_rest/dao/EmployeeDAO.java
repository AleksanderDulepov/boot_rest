package com.dulepov.spring.rest.boot_rest.dao;



import com.dulepov.spring.rest.boot_rest.entity.Employee;

import java.util.List;

public interface EmployeeDAO {

    public List<Employee> getAllEmployees();	//READ
    public void saveEmployee(Employee emp);		//CREATE
    public Employee getCurrentEmployee(int id);	//UPDATE
    public void deleteEmployee(int id);	//DELETE

}
