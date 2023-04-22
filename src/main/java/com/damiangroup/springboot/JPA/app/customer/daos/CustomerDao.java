package com.damiangroup.springboot.JPA.app.customer.daos;

import com.damiangroup.springboot.JPA.app.customer.entities.Customer;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CustomerDao extends PagingAndSortingRepository<Customer, Long> {

}

