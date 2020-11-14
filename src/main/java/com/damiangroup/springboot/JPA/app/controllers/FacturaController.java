package com.damiangroup.springboot.JPA.app.controllers;

import java.util.List;
import javax.validation.Valid;
import com.damiangroup.springboot.JPA.app.models.entity.Cliente;
import com.damiangroup.springboot.JPA.app.models.entity.Factura;
import com.damiangroup.springboot.JPA.app.models.entity.ItemFactura;
import com.damiangroup.springboot.JPA.app.models.entity.Producto;
import com.damiangroup.springboot.JPA.app.models.service.IClienteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/factura")
@SessionAttributes("factura")
@CrossOrigin(origins = "*")
public class FacturaController {

	@Autowired
	private IClienteService clienteService;
	
	private final Logger logging = LoggerFactory.getLogger(getClass());

	@GetMapping("/form/{clienteId}")
	public String crear(@PathVariable(value = "clienteId") Long clienteId, Model model, RedirectAttributes flash) {

		Cliente cliente = clienteService.findOne(clienteId);

		if (cliente == null) {
			flash.addFlashAttribute("error", "No existe un cliente con ese id");
			return "redirect:/listar";
		}
		Factura factura = new Factura();
		factura.setCliente(cliente);
		model.addAttribute("factura", factura);
		return "factura/form";

	}

	@PostMapping("/form/")
	public String guardar(@Valid Factura factura, BindingResult result ,
			@RequestParam(name = "item_id[]", required = false) Long[] itemId,
			@RequestParam(name = "cantidad[]", required = false) Integer[] cantidad,
			RedirectAttributes flash,
			SessionStatus status,
			Model model) {
		
		
		if(result.hasErrors()) {
			model.addAttribute("titulo","Crear factura");
			model.addAttribute("factura",factura);
			return "factura/form";
		}
		
		if (itemId == null || itemId.length == 0) {
			model.addAttribute("titulo","Crear factura");
			model.addAttribute("error","La factura debe contener lineas" );
			return "factura/form";
		}
		 
		for (int i = 0; i < itemId.length; i++) {
			Producto producto = clienteService.findProductoById(itemId[i]);
			ItemFactura itemFactura = new ItemFactura(cantidad[i],producto);
			factura.addItemFactura(itemFactura);
			logging.info("ID: "+itemId[i],toString());
		}
		
		clienteService.saveFactura(factura);
		status.setComplete();
		flash.addFlashAttribute("success","Factura Creada con éxito");
		return "redirect:/ver/"+factura.getCliente().getId();
	}
	
	@GetMapping("/ver/{id}")
	public String ver(@PathVariable(value  = "id") Long id,Model model,RedirectAttributes flash) {
		Factura factura = clienteService.findFacturaById(id);
		if (factura == null) {
			flash.addFlashAttribute("error","No existe esa factura");
			return "redirect:/listar";
		}
		
		model.addAttribute("factura",factura);
		model.addAttribute("titulo","Factura : ".concat(factura.getDescripcion()));
		return "factura/ver";
		
	}

	@GetMapping("/eliminar/{id}")
	public String eliminar(@PathVariable(value = "id") Long id, RedirectAttributes flash){

		Factura factura = clienteService.findFacturaById(id);
		if(factura==null){
			flash.addFlashAttribute("error","No existe una factura con ese id");
			return "redirect:/listar";
		}

		clienteService.deleteFactura(id);
		flash.addFlashAttribute("success","Factura eliminada con éxito");
		return "redirect:/ver/"+factura.getId();
	}

	@GetMapping(value = "/cargar-productos/{term}", produces = { "application/json" })
	public @ResponseBody List<Producto> cargarProductos(@PathVariable(value = "term") String term) {
		return clienteService.findByNombre(term);
	}

	@GetMapping(value = "/cargar-todos-los-productos", produces = { "application/json" })
	public @ResponseBody List<Producto> cargarTodosProductos() {
		return clienteService.findAllProducts();
	}

}
