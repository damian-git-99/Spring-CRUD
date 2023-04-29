package com.damiangroup.springboot.JPA.app.services;

import com.damiangroup.springboot.JPA.app.models.Customer;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

public interface CustomerService {
    Page<Customer> findAllCustomers(Pageable Pageable);

    void saveCustomer(Customer customer, MultipartFile photo);

    Customer findCustomerById(Long id);

    void deleteCustomerById(Long id);
}
