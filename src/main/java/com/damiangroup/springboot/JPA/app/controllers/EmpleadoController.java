package com.damiangroup.springboot.JPA.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.damiangroup.springboot.JPA.app.models.dao.IEmpleadoDao;
import com.damiangroup.springboot.JPA.app.models.entity.Empleado;

@Controller
public class EmpleadoController {
	
	@Autowired
	private IEmpleadoDao empleadosDao;
	
	@GetMapping("/empleados")
	public String listar(Model model) {
		model.addAttribute("empleados", empleadosDao.findAll());
		model.addAttribute("titulo", "listado de Empleados");
		return "empleados";
	}
	
	
	
}
