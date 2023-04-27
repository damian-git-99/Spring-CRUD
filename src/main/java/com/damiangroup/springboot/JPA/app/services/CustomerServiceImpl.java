package com.damiangroup.springboot.JPA.app.services;

import com.damiangroup.springboot.JPA.app.daos.CustomerDao;
import com.damiangroup.springboot.JPA.app.models.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerDao customerDao;
    private final PhotoService photoService;

    @Autowired
    public CustomerServiceImpl(CustomerDao customerDao, PhotoService photoService) {
        this.customerDao = customerDao;
        this.photoService = photoService;
    }

    @Override
    @Transactional
    public void saveCustomer(Customer customer, MultipartFile photo) {
        if (photo != null && !photo.isEmpty()) {
            String photoName = savePhotoCustomer(customer, photo);
            customer.setPhoto(photoName);
        }
        customerDao.save(customer);
    }

    protected String savePhotoCustomer(Customer customer, MultipartFile photo) {
        if (customer.getId() != null && customer.getPhoto() != null && photo.isEmpty())
            return customer.getPhoto();
        if (customer.getId() != null
                && customer.getPhoto() != null
                && !customer.getPhoto().isEmpty()) {
            // Delete previous photo
            photoService.deletePhoto(customer);
        }
        return photoService.savePhoto(photo, customer);
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
