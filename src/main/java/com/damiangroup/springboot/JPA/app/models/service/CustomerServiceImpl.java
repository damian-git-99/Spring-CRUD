package com.damiangroup.springboot.JPA.app.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.damiangroup.springboot.JPA.app.models.dao.CustomerDao;
import com.damiangroup.springboot.JPA.app.models.dao.InvoiceDao;
import com.damiangroup.springboot.JPA.app.models.dao.ProductDao;
import com.damiangroup.springboot.JPA.app.models.entity.Cliente;
import com.damiangroup.springboot.JPA.app.models.entity.Factura;
import com.damiangroup.springboot.JPA.app.models.entity.Producto;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerDao customerDao;
    private final ProductDao productDao;
    private final InvoiceDao invoiceDao;

    @Autowired
    public CustomerServiceImpl(CustomerDao customerDao, ProductDao productDao, InvoiceDao invoiceDao) {
        this.customerDao = customerDao;
        this.productDao = productDao;
        this.invoiceDao = invoiceDao;
    }

    @Override
    @Transactional
    public List<Cliente> findAll() {
        return (List<Cliente>) customerDao.findAll();
    }

    @Override
    @Transactional
    public void save(Cliente cliente) {
        customerDao.save(cliente);
    }

    @Override
    @Transactional
    public Cliente findOne(Long id) {
        return customerDao.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        customerDao.deleteById(id);
    }

    @Override
    @Transactional
    public Page<Cliente> findAll(Pageable pageable) {
        return customerDao.findAll(pageable);
    }

    @Override
    @Transactional
    public List<Producto> findByNombre(String term) {
        return productDao.findByNombre(term);
    }

    @Override
    @Transactional
    public List<Producto> findAllProducts() {
        return productDao.findAll();
    }

    @Override
    @Transactional
    public void saveFactura(Factura factura) {
        invoiceDao.save(factura);
    }

    @Override
    @Transactional
    public Producto findProductoById(Long id) {
        return productDao.findById(id).orElse(null);
    }


    @Override
    @Transactional
    public Factura findFacturaById(Long id) {
        return invoiceDao.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void deleteFactura(Long id) {
        invoiceDao.deleteById(id);
    }


}
