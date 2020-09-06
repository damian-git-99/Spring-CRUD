package com.damiangroup.springboot.JPA.app.models.service;

import java.util.List;
import com.damiangroup.springboot.JPA.app.models.entity.Cliente;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;



public interface IClienteService  {

    public List<Cliente> findAll();

    public Page<Cliente> findAll(Pageable Pageable);

	public void save(Cliente cliente);
	
	public Cliente findOne(Long id);
	
    public void delete(Long id);
	
}
