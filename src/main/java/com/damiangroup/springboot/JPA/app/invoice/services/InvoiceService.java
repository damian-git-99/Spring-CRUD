package com.damiangroup.springboot.JPA.app.invoice.services;

import com.damiangroup.springboot.JPA.app.invoice.entities.Invoice;
import com.damiangroup.springboot.JPA.app.invoice.entities.Product;

import java.util.List;

public interface InvoiceService {
    List<Product> findProductByProductName(String term);

    List<Product> findAllProducts();

    void saveInvoice(Invoice invoice);

    Product findProductById(Long id);

    Invoice findInvoiceById(Long id);

    void deleteInvoiceById(Long id);
}
