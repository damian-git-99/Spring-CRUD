package com.damiangroup.springboot.JPA.app.services;

import com.damiangroup.springboot.JPA.app.models.Customer;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomerService {
    Page<Customer> findAllCustomers(Pageable Pageable);

    void saveCustomer(Customer customer);

    Customer findCustomerById(Long id);

    void deleteCustomerById(Long id);
}
