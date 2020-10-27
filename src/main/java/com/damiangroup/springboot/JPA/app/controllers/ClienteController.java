package com.damiangroup.springboot.JPA.app.controllers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
import javax.validation.Valid;
import com.damiangroup.springboot.JPA.app.models.entity.Cliente;
import com.damiangroup.springboot.JPA.app.models.service.IClienteService;
import com.damiangroup.springboot.JPA.app.util.paginator.PageRender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ClienteController {
	
	private final String DIRECTORIOIMAGENES = "uploads/";

	@Autowired
	private IClienteService clienteService;

	/*
	 * 
	 * Ver mas informacion del cliente, tambien la foto
	 */
	@GetMapping("/ver/{id}")
	public String ver(@PathVariable(value = "id") Long id, Model model, RedirectAttributes flash) {
		Cliente cliente = clienteService.findOne(id);
		if (cliente == null) {
			flash.addFlashAttribute("error", "El cliente no existe");
			return "redirect:/listar";
		}

		model.addAttribute("cliente", cliente);
		model.addAttribute("titulo", "Detalle cliente: " + cliente.getNombre());
		return "ver";
	}

	/*
	 * Listar clientes
	 */
	@GetMapping("/listar")
	public String listar(@RequestParam(name = "page", defaultValue = "0") int page, Model model) {
		Pageable pageRequest = PageRequest.of(page, 5);

		// Page es un iterable
		Page<Cliente> clientes = clienteService.findAll(pageRequest);
		PageRender<Cliente> pageRender = new PageRender<>("/listar", clientes);

		model.addAttribute("page", pageRender);
		model.addAttribute("titulo", "listado de clientes");
		model.addAttribute("clientes", clientes);
		return "listar";
	}

	/*
	 * Formulario GET para registrar o actualizar un cliente
	 */
	@GetMapping("/form")
	public String form(Model model) {
		Cliente cliente = new Cliente();
		model.addAttribute("cliente", cliente);
		model.addAttribute("titulo", "Formulario de Cliente");
		return "form";
	}

	/*
	 * Formulario POST para registrar o actualizar un cliente
	 */
	@PostMapping("/form")
	public String guardar(@Valid Cliente cliente, BindingResult result, Model model, RedirectAttributes flash,
			@RequestParam("file") MultipartFile foto) {

		String urlFoto = guardarFoto(foto, cliente, flash);
		if (urlFoto != null)
			cliente.setFoto(urlFoto);

		if (result.hasErrors()) {
			model.addAttribute("cliente", cliente);
			return "/form";
		}

		clienteService.save(cliente);
		flash.addFlashAttribute("success", "Cliente Creado o actualizado con exito");
		return "redirect:/listar";
	}

	/*
	 * Se encarga de subir la foto y retornar el nombre de la foto
	 */
	private String guardarFoto(MultipartFile foto, Cliente cliente, RedirectAttributes flash) {

		if (foto.isEmpty()) {
			flash.addFlashAttribute("danger", "Ha ocurrido un error al intenta subir la foto");
			return null;
		}

		// Si existe el id quiere decir que el cliente ya existe y si la foto
		// es diferente de null quiere decir que el usuario actualizo la foto
		// pr lo cual hay que borrar la foto anterior
		if (cliente.getId() != null && cliente.getFoto() != null) {
			eliminarFoto(cliente.getId(), flash);
		}

		// Path directorioRecursos = Paths.get("src//main//resources//static/uploads");
		// String rootPath = directorioRecursos.toFile().getAbsolutePath();
		String uniqueFilename = UUID.randomUUID().toString() + "_" + foto.getOriginalFilename();
		Path rootPath = Paths.get("uploads").resolve(uniqueFilename);
		Path rootAbsolutPath = rootPath.toAbsolutePath();
		
		
		try {
			Files.copy(foto.getInputStream(), rootAbsolutPath);
			flash.addFlashAttribute("info", "Ha subido correctamente la foto: ".concat(uniqueFilename));
			return (uniqueFilename);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	@GetMapping("/form/{id}")
	public String editar(@PathVariable(value = "id") Long id, Model model, RedirectAttributes flash) {
		Cliente cliente = null;
		if (id > 0) {
			cliente = clienteService.findOne(id);
			if (cliente == null) {
				flash.addFlashAttribute("error", "No existe un cliente con el id: ".concat(id.toString()));
				return "redirect:/listar";
			}
		} else {
			flash.addFlashAttribute("error", "Id invalido");
			return "redirect:/listar";
		}

		model.addAttribute("titulo", "Editar Cliente");
		model.addAttribute("cliente", cliente);
		return "form";
	}

	@GetMapping("/eliminar/{id}")
	public String eliminar(@PathVariable(value = "id") Long id, Model model, RedirectAttributes flash) {

		if (id > 0) {
			eliminarFoto(id, flash);
			clienteService.delete(id);
		}

		flash.addFlashAttribute("success", "Cliente eliminado con exito");
		return "redirect:/listar";
	}

	private void eliminarFoto(Long id, RedirectAttributes flash) {
		// Eliminar foto del cliente
		Cliente cliente = clienteService.findOne(id);
		Path rootPaths = Paths.get("uploads").resolve(cliente.getFoto()).toAbsolutePath();
		File archivoFile = rootPaths.toFile();
		if (archivoFile.exists() && archivoFile.canRead()) {
			if (archivoFile.delete()) {
				flash.addFlashAttribute("success", "Foto eliminada");
			}
		}
	}

}
