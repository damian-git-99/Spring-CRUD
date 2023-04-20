package com.damiangroup.springboot.JPA.app.models.dao;

import com.damiangroup.springboot.JPA.app.models.entity.Invoice;
import org.springframework.data.repository.CrudRepository;

public interface InvoiceDao extends CrudRepository<Invoice, Long> {

}
