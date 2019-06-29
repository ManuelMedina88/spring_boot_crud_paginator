package com.newtech.information.technology.app.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.newtech.information.technology.app.model.dao.IEmployeeDao;
import com.newtech.information.technology.app.model.entities.Employee;

@Service
public class EmployeeServiceImpl implements IEmployeeService {

	@Autowired
	private IEmployeeDao employeeDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<Employee> findAll() {
		// TODO Auto-generated method stub
		return (List<Employee>)employeeDao.findAll();
	}
	
	@Override
	@Transactional(readOnly = true)
	public Page<Employee> findAll(Pageable pageable){

		return employeeDao.findAll(pageable);
		
	}

}
