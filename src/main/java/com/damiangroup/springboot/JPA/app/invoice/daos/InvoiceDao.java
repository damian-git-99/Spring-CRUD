package com.damiangroup.springboot.JPA.app.invoice.daos;

import com.damiangroup.springboot.JPA.app.invoice.entities.Invoice;
import org.springframework.data.repository.CrudRepository;

public interface InvoiceDao extends CrudRepository<Invoice, Long> {

}
