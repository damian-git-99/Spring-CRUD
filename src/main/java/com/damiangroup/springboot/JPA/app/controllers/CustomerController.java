package com.damiangroup.springboot.JPA.app.controllers;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.damiangroup.springboot.JPA.app.models.entity.Customer;
import com.damiangroup.springboot.JPA.app.models.service.CustomerService;
import com.damiangroup.springboot.JPA.app.models.service.IUploadFileService;
import com.damiangroup.springboot.JPA.app.util.paginator.PageRender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@SessionAttributes("customer")
public class CustomerController {

    private final CustomerService customerService;
    private final IUploadFileService uploadFile;

    @Autowired
    public CustomerController(CustomerService customerService, IUploadFileService uploadFile) {
        this.customerService = customerService;
        this.uploadFile = uploadFile;
    }

    @GetMapping("/ver/{id}")
    public String ver(@PathVariable(value = "id") Long id, Model model, RedirectAttributes flash) {
        Customer customer = customerService.findOne(id);
        if (customer == null) {
            flash.addFlashAttribute("error", "Customer does not exist");
            return "redirect:/";
        }
        model.addAttribute("customer", customer);
        model.addAttribute("titulo", "Detalle customer: " + customer.getName());
        return "ver";
    }

    @GetMapping("/")
    public String home(@RequestParam(name = "page", defaultValue = "0") int page, Model model) {
        Pageable pageRequest = PageRequest.of(page, 5);
        Page<Customer> customers = customerService.findAll(pageRequest);
        PageRender<Customer> pageRender = new PageRender<>("/", customers);

        model.addAttribute("page", pageRender);
        model.addAttribute("customers", customers);
        return "home";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/form")
    public String createCustomerForm(Model model) {
        Customer customer = new Customer();
        model.addAttribute("customer", customer);
        model.addAttribute("titulo", "Customer Form");
        return "form";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/form/{id}")
    public String editCustomerForm(@PathVariable(value = "id") Long id, Model model, RedirectAttributes flash) {
        Customer customer = customerService.findOne(id);
        if (customer == null) {
            flash.addFlashAttribute("error", "User Not found");
            return "redirect:/";
        }
        model.addAttribute("titulo", "Editar Customer");
        model.addAttribute("customer", customer);
        return "form";
    }

    @Secured("ROLE_ADMIN")
    @PostMapping("/form")
    public String createOrEditCustomer(@Valid Customer customer, BindingResult result, Model model, RedirectAttributes flash,
                          @RequestParam("file") MultipartFile foto, SessionStatus status) {

        String urlFoto;
        try {
            urlFoto = uploadFile.guardarFoto(foto, customer);
        } catch (IOException e) {
            e.printStackTrace();
            urlFoto = "";
        }
        customer.setPhoto(urlFoto);

        if (result.hasErrors()) {
            model.addAttribute("customer", customer);
            return "/form";
        }
        customerService.save(customer);
        flash.addFlashAttribute("success", "Customer Creado o actualizado con exito");
        status.setComplete();
        return "redirect:/";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable(value = "id") Long id, RedirectAttributes flash) {

        if (id > 0) {
            Customer customer = customerService.findOne(id);
            if (customer == null) {
                flash.addFlashAttribute("error", "No existe un customer con el id: ".concat(id.toString()));
                return "redirect:/";
            }
            if (uploadFile.eliminarFoto(customer)) {
                flash.addFlashAttribute("success", "Imagen Borrada: ".concat(customer.getPhoto()));
            } else
                flash.addFlashAttribute("error",
                        "La imagen no se pudo borrar: (El customer no tiene imagen o hubo un error al intentar borrarla)"
                                .concat(customer.getPhoto()));
            customerService.delete(id);
        }

        flash.addFlashAttribute("success", "Customer eliminado con exito");
        return "redirect:/";
    }

}
