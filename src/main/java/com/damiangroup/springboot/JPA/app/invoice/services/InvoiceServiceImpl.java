package com.damiangroup.springboot.JPA.app.invoice.services;

import com.damiangroup.springboot.JPA.app.invoice.daos.InvoiceDao;
import com.damiangroup.springboot.JPA.app.invoice.daos.ProductDao;
import com.damiangroup.springboot.JPA.app.invoice.entities.Invoice;
import com.damiangroup.springboot.JPA.app.invoice.entities.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class InvoiceServiceImpl implements InvoiceService {

    private final ProductDao productDao;
    private final InvoiceDao invoiceDao;

    @Autowired
    public InvoiceServiceImpl(ProductDao productDao, InvoiceDao invoiceDao) {
        this.productDao = productDao;
        this.invoiceDao = invoiceDao;
    }

    @Override
    @Transactional
    public List<Product> findByNombre(String term) {
        return productDao.findByNombre(term);
    }

    @Override
    @Transactional
    public List<Product> findAllProducts() {
        return productDao.findAll();
    }

    @Override
    @Transactional
    public void saveFactura(Invoice invoice) {
        invoiceDao.save(invoice);
    }

    @Override
    @Transactional
    public Product findProductoById(Long id) {
        return productDao.findById(id).orElse(null);
    }


    @Override
    @Transactional
    public Invoice findFacturaById(Long id) {
        return invoiceDao.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void deleteFactura(Long id) {
        invoiceDao.deleteById(id);
    }
}
