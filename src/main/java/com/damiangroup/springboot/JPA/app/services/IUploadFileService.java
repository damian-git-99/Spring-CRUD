package com.damiangroup.springboot.JPA.app.services;

import java.io.IOException;
import com.damiangroup.springboot.JPA.app.models.Customer;
import org.springframework.web.multipart.MultipartFile;


public interface IUploadFileService {
    public String guardarFoto(MultipartFile foto, Customer customer) throws IOException;
    public boolean eliminarFoto(Customer customer);
}