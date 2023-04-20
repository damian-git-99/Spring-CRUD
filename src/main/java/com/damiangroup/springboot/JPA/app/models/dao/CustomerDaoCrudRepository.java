package com.damiangroup.springboot.JPA.app.models.dao;

import com.damiangroup.springboot.JPA.app.models.entity.Cliente;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CustomerDaoCrudRepository extends PagingAndSortingRepository<Cliente, Long> {
    //@Query("Select * from Cliente c where c.email = ?1 ")
    //Cliente findByEmail(String email);
}

