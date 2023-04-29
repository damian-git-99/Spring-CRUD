package com.damiangroup.springboot.JPA.app.services;

import com.damiangroup.springboot.JPA.app.daos.InvoiceDao;
import com.damiangroup.springboot.JPA.app.daos.ProductDao;
import com.damiangroup.springboot.JPA.app.models.Invoice;
import com.damiangroup.springboot.JPA.app.models.Product;
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
    public List<Product> findProductByProductName(String term) {
        return productDao.findProductByProductName(term);
    }

    @Override
    @Transactional
    public List<Product> findAllProducts() {
        return productDao.findAll();
    }

    @Override
    @Transactional
    public void saveInvoice(Invoice invoice) {
        invoiceDao.save(invoice);
    }

    @Override
    @Transactional
    public Product findProductById(Long id) {
        return productDao.findById(id).orElse(null);
    }


    @Override
    @Transactional
    public Invoice findInvoiceById(Long id) {
        return invoiceDao.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void deleteInvoiceById(Long id) {
        invoiceDao.deleteById(id);
    }
}
