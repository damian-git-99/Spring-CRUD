package com.damiangroup.springboot.JPA.app.models.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
import com.damiangroup.springboot.JPA.app.models.entity.Cliente;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UploadFileServiceImpl implements IUploadFileService {

    private final String DIRECTORIO_IMAGENES = "uploads/";

    @Override
    public String guardarFoto(MultipartFile foto, Cliente cliente) throws IOException {
        if (cliente.getId() != null && cliente.getFoto() != null && foto.isEmpty()) {
            return cliente.getFoto();
        }
        if (foto.isEmpty() || foto == null)
            return "";

        // Si existe el id quiere decir que el cliente ya existe y si la foto
        // es diferente de null quiere decir que el usuario actualizo la foto
        // pr lo cual hay que borrar la foto anterior
        if (cliente.getId() != null && cliente.getFoto() != null) {
            eliminarFoto(cliente);
        }

        // String uniqueFilename = UUID.randomUUID().toString() + "_" +
        // foto.getOriginalFilename();
        // String rutaDestino = DIRECTORIO_IMAGENES + uniqueFilename;
        // File destinoFile = new File(rutaDestino);
        // Files.copy(foto.getInputStream(), destinoFile.toPath());
        // return uniqueFilename;
        String uniqueFilename = UUID.randomUUID().toString() + "_" + foto.getOriginalFilename();
        Path rootPath = Paths.get(DIRECTORIO_IMAGENES).resolve(uniqueFilename).toAbsolutePath();
        Files.copy(foto.getInputStream(), rootPath);
        return uniqueFilename;

    }

    @Override
    public boolean eliminarFoto(Cliente cliente) {
        // Eliminar foto del cliente
        // Cliente cliente = clienteService.findOne(id);
        // String fileName = DIRECTORIO_IMAGENES + cliente.getFoto();
        // File archivoFile = new File(fileName);
        Path rootPath = Paths.get(DIRECTORIO_IMAGENES).resolve(cliente.getFoto()).toAbsolutePath();
        File archivoFile = rootPath.toFile();
        if (archivoFile.exists() && archivoFile.canRead()) {
            if (archivoFile.delete())
                return true;
        }
        return false;
    }

}
