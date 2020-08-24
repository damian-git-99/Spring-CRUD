package com.damiangroup.springboot.JPA.app.controllers;

import javax.validation.Valid;
import com.damiangroup.springboot.JPA.app.models.dao.IClienteDao;
import com.damiangroup.springboot.JPA.app.models.entity.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class ClienteController {

    @Autowired
    private IClienteDao clienteDao;

    @GetMapping("/listar")
    public String listar(Model model){
        model.addAttribute("titulo", "listado de clientes");
        model.addAttribute("clientes", clienteDao.findAll());
        return "listar";
    }

    @GetMapping("/form")
    public String crear(Model model) {
    	Cliente cliente = new Cliente();
    	model.addAttribute("cliente", cliente);
    	model.addAttribute("titulo", "Formulario de Cliente");
    	return "form";
    }
    
    @PostMapping("/form")
    public String guardar(@Valid Cliente cliente, BindingResult result, Model model) {
        if (result.hasErrors()) {
           
            return "/form";
        }
        clienteDao.save(cliente);
    	return "redirect:listar";
    }
}
