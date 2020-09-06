package com.damiangroup.springboot.JPA.app.models.dao;

import com.damiangroup.springboot.JPA.app.models.entity.Cliente;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface IClienteDaoCrudRepository extends PagingAndSortingRepository<Cliente, Long>{
    
}
