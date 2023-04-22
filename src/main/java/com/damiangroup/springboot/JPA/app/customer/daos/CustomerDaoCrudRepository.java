package com.damiangroup.springboot.JPA.app.customer.daos;

import com.damiangroup.springboot.JPA.app.customer.entities.Customer;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CustomerDaoCrudRepository extends PagingAndSortingRepository<Customer, Long> {

}

