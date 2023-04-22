package com.damiangroup.springboot.JPA.app.invoice.controllers;

import java.util.List;
import javax.validation.Valid;

import com.damiangroup.springboot.JPA.app.customer.entities.Customer;
import com.damiangroup.springboot.JPA.app.customer.service.CustomerService;
import com.damiangroup.springboot.JPA.app.invoice.entities.Invoice;
import com.damiangroup.springboot.JPA.app.invoice.entities.InvoiceItem;
import com.damiangroup.springboot.JPA.app.invoice.entities.Product;
import com.damiangroup.springboot.JPA.app.invoice.services.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
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

@Secured("ROLE_ADMIN")
@Controller
@RequestMapping("/factura")
@SessionAttributes("invoice")
@CrossOrigin(origins = "*")
public class InvoiceController {

    private final InvoiceService invoiceService;
    private final CustomerService customerService;

    @Autowired
    public InvoiceController(InvoiceService invoiceService, CustomerService customerService) {
        this.invoiceService = invoiceService;
        this.customerService = customerService;
    }

    @GetMapping("/form/{customerId}")
    public String createInvoiceForm(@PathVariable(value = "customerId") Long customerId, Model model, RedirectAttributes flash) {
        Customer customer = customerService.findOne(customerId);
        if (customer == null) {
            flash.addFlashAttribute("error", "No existe un customer con ese id");
            return "redirect:/home";
        }
        Invoice invoice = new Invoice();
        invoice.setCustomer(customer);
        model.addAttribute("invoice", invoice);
        return "factura/form";
    }

    @PostMapping("/form/")
    public String createInvoicePost(@Valid Invoice invoice, BindingResult result,
                                    @RequestParam(name = "item_id[]", required = false) Long[] itemId,
                                    @RequestParam(name = "cantidad[]", required = false) Integer[] cantidad,
                                    RedirectAttributes flash,
                                    SessionStatus status,
                                    Model model) {

        if (result.hasErrors()) {
            model.addAttribute("titulo", "Crear invoice");
            model.addAttribute("invoice", invoice);
            return "factura/form";
        }

        if (itemId == null || itemId.length == 0) {
            model.addAttribute("titulo", "Crear invoice");
            model.addAttribute("error", "La invoice debe contener lineas");
            return "factura/form";
        }

        for (int i = 0; i < itemId.length; i++) {
            Product product = invoiceService.findProductoById(itemId[i]);
            InvoiceItem invoiceItem = new InvoiceItem(cantidad[i], product);
            invoice.addItemFactura(invoiceItem);
        }

        invoiceService.saveFactura(invoice);
        status.setComplete();
        flash.addFlashAttribute("success", "Invoice Creada con éxito");
        return "redirect:/ver/" + invoice.getCustomer().getId();
    }

    @GetMapping("/ver/{id}")
    public String ver(@PathVariable(value = "id") Long id, Model model, RedirectAttributes flash) {
        Invoice invoice = invoiceService.findFacturaById(id);
        if (invoice == null) {
            flash.addFlashAttribute("error", "No existe esa invoice");
            return "redirect:/home";
        }

        model.addAttribute("invoice", invoice);
        model.addAttribute("titulo", "Invoice : ".concat(invoice.getDescription()));
        return "factura/ver";

    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable(value = "id") Long id, RedirectAttributes flash) {

        Invoice invoice = invoiceService.findFacturaById(id);
        if (invoice == null) {
            flash.addFlashAttribute("error", "No existe una invoice con ese id");
            return "redirect:/home";
        }

        invoiceService.deleteFactura(id);
        flash.addFlashAttribute("success", "Invoice eliminada con éxito");
        return "redirect:/ver/" + invoice.getId();
    }

    @GetMapping(value = "/cargar-productos/{term}", produces = {"application/json"})
    public @ResponseBody List<Product> cargarProductos(@PathVariable(value = "term") String term) {
        return invoiceService.findByNombre(term);
    }

    @GetMapping(value = "/cargar-todos-los-productos", produces = {"application/json"})
    public @ResponseBody List<Product> cargarTodosProductos() {
        return invoiceService.findAllProducts();
    }

}
