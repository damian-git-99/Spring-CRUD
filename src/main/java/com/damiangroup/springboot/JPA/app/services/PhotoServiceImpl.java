package com.damiangroup.springboot.JPA.app.services;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.UUID;

import com.damiangroup.springboot.JPA.app.models.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

@Service
public class PhotoServiceImpl implements PhotoService {

    private final String DIRECTORY = "uploads/";

    public PhotoServiceImpl() {
        this.createDirectory();
    }

    @Override
    public String savePhoto(MultipartFile photo, Customer customer) throws IOException {
        // todo: remove all customer code
        if (customer.getId() != null && customer.getPhoto() != null && photo.isEmpty())
            return customer.getPhoto();

        if (photo.isEmpty() || photo == null)
            return "";

        // Si existe el id quiere decir que el customer ya existe y si la foto
        // es diferente de null quiere decir que el usuario actualizo la foto
        // pr lo cual hay que borrar la foto anterior
        if (customer.getId() != null && customer.getPhoto() != null && !customer.getPhoto().isEmpty()) {
            deletePhoto(customer);
        }

        String uniqueFilename = UUID.randomUUID().toString() + "_" + photo.getOriginalFilename();
        String rutaDestino = DIRECTORY + uniqueFilename;
        File destinoFile = new File(rutaDestino);
        Files.copy(photo.getInputStream(), destinoFile.toPath());
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
