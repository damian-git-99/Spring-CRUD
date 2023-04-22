package com.damiangroup.springboot.JPA.app.invoice.services;

import com.damiangroup.springboot.JPA.app.invoice.entities.Invoice;
import com.damiangroup.springboot.JPA.app.invoice.entities.Product;

import java.util.List;

public interface InvoiceService {
    public List<Product> findByNombre(String term);

    public List<Product> findAllProducts();

    public void saveFactura(Invoice invoice);

    public Product findProductoById(Long id);

    public Invoice findFacturaById(Long id);

    public void deleteFactura(Long id);
}
