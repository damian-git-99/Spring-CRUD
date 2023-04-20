package com.damiangroup.springboot.JPA.app.models.service;

import java.util.List;

import com.damiangroup.springboot.JPA.app.models.entity.Customer;
import com.damiangroup.springboot.JPA.app.models.entity.Invoice;
import com.damiangroup.springboot.JPA.app.models.entity.Product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

// todo: separate interface
public interface CustomerService {

    public List<Customer> findAll();

    public Page<Customer> findAll(Pageable Pageable);

    public void save(Customer customer);

    public Customer findOne(Long id);

    public void delete(Long id);

    public List<Product> findByNombre(String term);

    public List<Product> findAllProducts();

    public void saveFactura(Invoice invoice);

    public Product findProductoById(Long id);

    public Invoice findFacturaById(Long id);

    public void deleteFactura(Long id);


}
