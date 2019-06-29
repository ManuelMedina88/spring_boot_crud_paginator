package com.newtech.information.technology.app.model.dao;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.newtech.information.technology.app.model.entities.Employee;

@Repository
public interface IEmployeeDao extends PagingAndSortingRepository<Employee, Long>{

}
