package com.damiangroup.springboot.JPA.app.customer.daos;

import com.damiangroup.springboot.JPA.app.customer.entities.Customer;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CustomerDaoCrudRepository extends PagingAndSortingRepository<Customer, Long> {
    //@Query("Select * from Customer c where c.email = ?1 ")
    //Customer findByEmail(String email);
}

