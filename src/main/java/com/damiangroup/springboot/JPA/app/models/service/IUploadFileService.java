package com.damiangroup.springboot.JPA.app.models.service;

import java.io.IOException;
import com.damiangroup.springboot.JPA.app.models.entity.Cliente;
import org.springframework.web.multipart.MultipartFile;


public interface IUploadFileService {
    public String guardarFoto(MultipartFile foto, Cliente cliente) throws IOException;
    public boolean eliminarFoto(Cliente cliente);
}
