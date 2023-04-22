package com.damiangroup.springboot.JPA.app.customer.service;

import com.damiangroup.springboot.JPA.app.customer.daos.CustomerDaoCrudRepository;
import com.damiangroup.springboot.JPA.app.customer.entities.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerDaoCrudRepository customerDao;

    @Autowired
    public CustomerServiceImpl(CustomerDaoCrudRepository customerDao) {
        this.customerDao = customerDao;
    }

    @Override
    @Transactional
    public void saveCustomer(Customer customer) {
        customerDao.save(customer);
    }

    @Override
    @Transactional
    public Customer findCustomerById(Long id) {
        return customerDao.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void deleteCustomerById(Long id) {
        customerDao.deleteById(id);
    }

    @Override
    @Transactional
    public Page<Customer> findAllCustomers(Pageable pageable) {
        return customerDao.findAll(pageable);
    }

}
