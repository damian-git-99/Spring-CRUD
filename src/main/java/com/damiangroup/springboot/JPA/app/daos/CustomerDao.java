package com.damiangroup.springboot.JPA.app.daos;

import com.damiangroup.springboot.JPA.app.models.Customer;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CustomerDao extends PagingAndSortingRepository<Customer, Long> {

}

