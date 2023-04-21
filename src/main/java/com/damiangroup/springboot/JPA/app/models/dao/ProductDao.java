package com.damiangroup.springboot.JPA.app.models.dao;

import java.util.List;

import com.damiangroup.springboot.JPA.app.invoice.Product;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface ProductDao extends CrudRepository<Product, Long> {

    @Query("select p from Product p where p.productName like %?1%")
    List<Product> findByNombre(String term);

    @Query("select p from Product p")
    List<Product> findAll();

}
