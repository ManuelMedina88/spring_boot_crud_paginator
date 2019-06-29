package com.newtech.information.technology.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.newtech.information.technology.app.model.entities.Employee;
import com.newtech.information.technology.app.model.service.IEmployeeService;
import com.newtech.information.technology.app.util.paginator.PageRender;

@Controller
@RequestMapping("/employee")
@SessionAttributes("employees")
public class EmployeeControllers {
	
	@Autowired
	private IEmployeeService employeeService;
	
	@GetMapping("/list")
	public ModelAndView showEmployeeList(@RequestParam(name="page", defaultValue="0") int page, ModelAndView model) {
		
		Pageable pageRequest = PageRequest.of(page, 2);
		
		Page<Employee> employees = employeeService.findAll(pageRequest);
		
		PageRender<Employee> pageRender = new PageRender<Employee>("/employee/list", employees);
		
		model.addObject("title", "List of Employee");
		model.addObject("page", pageRender);
		model.setViewName("list");
		
		return model;
	}

}
