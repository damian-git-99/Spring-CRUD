package com.damiangroup.springboot.JPA.app.services;

import java.io.IOException;

import com.damiangroup.springboot.JPA.app.models.Customer;
import org.springframework.web.multipart.MultipartFile;

public interface PhotoService {
    String savePhoto(MultipartFile photo, Customer customer) throws IOException;

    boolean deletePhoto(Customer customer);
}
