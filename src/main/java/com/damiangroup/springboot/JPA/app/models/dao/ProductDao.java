package com.damiangroup.springboot.JPA.app.models.dao;

import java.util.List;

import com.damiangroup.springboot.JPA.app.models.entity.Producto;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface ProductDao extends CrudRepository<Producto, Long> {

    @Query("select p from Producto p where p.nombre like %?1%")
    List<Producto> findByNombre(String term);

    @Query("select p from Producto p")
    List<Producto> findAll();

}
