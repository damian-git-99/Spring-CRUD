package com.damiangroup.springboot.JPA.app.models.dao;

import com.damiangroup.springboot.JPA.app.models.entity.Cliente;

import org.springframework.data.repository.CrudRepository;

public interface IClienteDaoCrudRepository extends CrudRepository<Cliente, Long>{
    
}
