package com.damiangroup.springboot.JPA.app.services;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.UUID;

import com.damiangroup.springboot.JPA.app.models.Customer;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class PhotoServiceImpl implements PhotoService {

    private final String DIRECTORY = "uploads/";

    public PhotoServiceImpl() {
        this.createDirectory();
    }

    @Override
    public String savePhoto(MultipartFile photo, Customer customer) {
        String uniqueFilename = UUID.randomUUID() + "_" + photo.getOriginalFilename();
        String path = DIRECTORY + uniqueFilename;
        File file = new File(path);
        try {
            Files.copy(photo.getInputStream(), file.toPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return uniqueFilename;
    }

    private void createDirectory() {
        File directory = new File(DIRECTORY);
        if (directory.exists() && directory.isDirectory()) return;
        directory.mkdirs();
    }

    @Override
    public boolean deletePhoto(Customer customer) {
        String fileName = DIRECTORY + customer.getPhoto();
        File file = new File(fileName);
        if (file.exists() && file.canRead()) {
            return file.delete();
        }
        return false;
    }

}
