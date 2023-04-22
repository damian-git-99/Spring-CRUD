package com.damiangroup.springboot.JPA.app.daos;

import com.damiangroup.springboot.JPA.app.models.Invoice;
import org.springframework.data.repository.CrudRepository;

public interface InvoiceDao extends CrudRepository<Invoice, Long> {

}
