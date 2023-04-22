package com.damiangroup.springboot.JPA.app.services;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.UUID;
import com.damiangroup.springboot.JPA.app.models.Customer;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UploadFileServiceImpl implements IUploadFileService {

	private final String DIRECTORIO_IMAGENES = "uploads/";

	@Override
	public String guardarFoto(MultipartFile foto, Customer customer) throws IOException {

		if (customer.getId() != null && customer.getPhoto() != null && foto.isEmpty())
			return customer.getPhoto();
		
		if (foto.isEmpty() || foto == null)
			return "";

		// Si existe el id quiere decir que el customer ya existe y si la foto
		// es diferente de null quiere decir que el usuario actualizo la foto
		// pr lo cual hay que borrar la foto anterior
		if (customer.getId() != null && customer.getPhoto() != null) {
			eliminarFoto(customer);
		}

		// Creamos la carpeta si no existe
		File folder = new File(DIRECTORIO_IMAGENES);
		folder.mkdir();

		// copiamos el archivo
		String uniqueFilename = UUID.randomUUID().toString() + "_" + foto.getOriginalFilename();
		String rutaDestino = DIRECTORIO_IMAGENES + uniqueFilename;
		File destinoFile = new File(rutaDestino);
		Files.copy(foto.getInputStream(), destinoFile.toPath());
		return uniqueFilename;

	}

	@Override
	public boolean eliminarFoto(Customer customer) {
		// Eliminar foto del customer
		String fileName = DIRECTORIO_IMAGENES + customer.getPhoto();
		File archivoFile = new File(fileName);
		if (archivoFile.exists() && archivoFile.canRead()) {
			if (archivoFile.delete())
				return true;
		}
		return false;
	}

}
