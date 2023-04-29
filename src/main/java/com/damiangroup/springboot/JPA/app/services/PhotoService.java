package com.damiangroup.springboot.JPA.app.services;

import org.springframework.web.multipart.MultipartFile;

public interface PhotoService {
    String savePhoto(MultipartFile photo);

    boolean deletePhoto(String photoName);
}
