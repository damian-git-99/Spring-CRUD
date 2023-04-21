package com.damiangroup.springboot.JPA.app.invoice;

import org.springframework.data.repository.CrudRepository;

public interface InvoiceDao extends CrudRepository<Invoice, Long> {

}
