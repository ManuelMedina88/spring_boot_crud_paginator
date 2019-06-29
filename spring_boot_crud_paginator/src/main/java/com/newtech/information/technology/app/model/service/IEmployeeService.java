package com.newtech.information.technology.app.model.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.newtech.information.technology.app.model.entities.Employee;

public interface IEmployeeService {

	
	public List<Employee> findAll();
	
	public Page<Employee> findAll(Pageable pageable);
	
}
