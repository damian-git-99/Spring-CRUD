package com.damiangroup.springboot.JPA.app.models.dao;

import org.springframework.data.repository.CrudRepository;
import com.damiangroup.springboot.JPA.app.models.entity.Factura;

public interface InvoiceDao extends CrudRepository<Factura, Long> {

}
