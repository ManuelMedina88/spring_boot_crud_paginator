package com.newtech.information.technology.app.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.newtech.information.technology.app.model.entities.Employee;
import com.newtech.information.technology.app.model.service.IEmployeeService;
import com.newtech.information.technology.app.util.paginator.PageRender;

@Controller
@RequestMapping("/employee")
@SessionAttributes("employee")
public class EmployeeControllers {
	
	@Autowired
	private IEmployeeService employeeService;
	
	@GetMapping("/list")
	public ModelAndView showEmployeeList(@RequestParam(name="page", defaultValue="0") int page, ModelAndView model) {
		
		Pageable pageRequest = PageRequest.of(page, 3);
		
		Page<Employee> employees = employeeService.findAll(pageRequest);
		
		PageRender<Employee> pageRender = new PageRender<Employee>("/employee/list", employees);
		model.addObject("title","List of Employees");
		model.addObject("employees", employees);
		model.addObject("page", pageRender);
		model.setViewName("list");
		
		return model;
	}
	
	@GetMapping(value="/look/{id}")
	public String look(@PathVariable(value="id") Long id, Model model){

		Employee employee = employeeService.findById(id);
		
		if(employee == null){
			return "redirect:/employee/list";
		}
		
		model.addAttribute("employee", employee);
		model.addAttribute("title", "Employeee Details:" + employee.getName());
		
		return "look";
	}
	
	
	@GetMapping("/form")
	public String registerEmployee(Model model) {
		
		Employee employee = new Employee();
		model.addAttribute("title", "Registering Employee");
		model.addAttribute("employee", employee);
		return "/form";
	}
	
	@GetMapping("/form/{id}")
	public String modifyingEmployee(@PathVariable("id") Long id, Model model) {
		
		Employee employee = employeeService.findById(id);
		model.addAttribute("title", "Modifying Employee");
		model.addAttribute("employee", employee);
		return "form";
	}
	
	@PostMapping("/form")
	public String saveEmployee(@Valid Employee employee, BindingResult result, Model model,
		@RequestParam("file") MultipartFile photo, RedirectAttributes flash, SessionStatus status){

		if(result.hasErrors()){
			
			model.addAttribute("title", "Form of Customer");
			return "form";
		}
		
		if(!photo.isEmpty()){
		
			String uniqueFilename = UUID.randomUUID().toString() + "_" + photo.getOriginalFilename();
			Path rootPath = Paths.get("uploads").resolve(uniqueFilename);
			
			Path rootAbsolutePath = rootPath.toAbsolutePath();
			
			try{
			
				Files.copy(photo.getInputStream(), rootAbsolutePath);
				
				employee.setPhoto(uniqueFilename);
			}catch(IOException ex){
				
				ex.printStackTrace();
			}
		}
		
		employeeService.save(employee);
		status.isComplete();
		return "redirect:/employee/list";
	}
	
	@GetMapping("/delete/{id}")
	public String deleteEmployee(@PathVariable("id")Long id, Model model) {
		
		employeeService.deleteById(id);
		
		return "redirect:/employee/list";
		
	}

}
