package com.damiangroup.springboot.JPA.app.controllers;

import com.damiangroup.springboot.JPA.app.models.entity.Customer;
import com.damiangroup.springboot.JPA.app.models.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/clientes")
public class ClienteRestController {

    @Autowired
    private CustomerService clienteService;

    @GetMapping("/listar")
    public List<Customer> listar(){
        return clienteService.findAll();
    }

}
