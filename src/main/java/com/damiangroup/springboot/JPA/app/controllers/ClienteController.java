package com.damiangroup.springboot.JPA.app.controllers;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

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
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@SessionAttributes("cliente")
public class ClienteController {

    private final CustomerService customerService;
    private final IUploadFileService uploadFile;


    @Autowired
    public ClienteController(CustomerService customerService, IUploadFileService uploadFile) {
        this.customerService = customerService;
        this.uploadFile = uploadFile;
    }

    @GetMapping("/ver/{id}")
    public String ver(@PathVariable(value = "id") Long id, Model model, RedirectAttributes flash) {
        Customer customer = customerService.findOne(id);
        if (customer == null) {
            flash.addFlashAttribute("error", "El customer no existe");
            return "redirect:/listar";
        }

        model.addAttribute("cliente", customer);
        model.addAttribute("titulo", "Detalle customer: " + customer.getName());
        return "ver";
    }

    @GetMapping({"/listar", "/"})
    public String listar(@RequestParam(name = "page", defaultValue = "0") int page, Model model,
                         Authentication authentication, HttpServletRequest request) {
        Pageable pageRequest = PageRequest.of(page, 5);

        Page<Customer> customers = customerService.findAll(pageRequest);
        PageRender<Customer> pageRender = new PageRender<>("/listar", customers);

        model.addAttribute("page", pageRender);
        model.addAttribute("customers", customers);
        return "listar";
    }

    /*
     * Formulario GET para registrar o actualizar un cliente
     */
    @Secured("ROLE_ADMIN")
    @GetMapping("/form")
    public String form(Model model) {
        Customer customer = new Customer();
        model.addAttribute("customer", customer);
        model.addAttribute("titulo", "Formulario de Customer");
        return "form";
    }

    /*
     * Formulario POST para registrar o actualizar un customer
     */
    @Secured("ROLE_ADMIN")
    @PostMapping("/form")
    public String guardar(@Valid Customer customer, BindingResult result, Model model, RedirectAttributes flash,
                          @RequestParam("file") MultipartFile foto, SessionStatus status) {

        String urlFoto;
        try {
            urlFoto = uploadFile.guardarFoto(foto, customer);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            urlFoto = "";
        }
        customer.setPhoto(urlFoto);

        if (result.hasErrors()) {
            model.addAttribute("cliente", customer);
            return "/form";
        }

        customerService.save(customer);
        flash.addFlashAttribute("info", "Imagen subida correctamente");
        flash.addFlashAttribute("success", "Customer Creado o actualizado con exito");
        status.setComplete();
        return "redirect:/listar";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/form/{id}")
    public String editar(@PathVariable(value = "id") Long id, Model model, RedirectAttributes flash) {
        Customer customer = customerService.findOne(id);
        if (customer == null) {
            flash.addFlashAttribute("error", "User Not found");
            return "redirect:/listar";
        }
        model.addAttribute("titulo", "Editar Customer");
        model.addAttribute("customer", customer);
        return "form";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable(value = "id") Long id, Model model, RedirectAttributes flash) {

        if (id > 0) {
            Customer customer = customerService.findOne(id);
            if (customer == null) {
                flash.addFlashAttribute("error", "No existe un customer con el id: ".concat(id.toString()));
                return "redirect:/listar";
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
        return "redirect:/listar";
    }

    private boolean hasRole(String role) {

        SecurityContext context = SecurityContextHolder.getContext();
        if (context == null) {
            return false;
        }

        Authentication authentication = context.getAuthentication();
        if (authentication == null) {
            return false;
        }

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        for (GrantedAuthority grantedAuthority : authorities) {
            if (role.equals(grantedAuthority.getAuthority())) {
                return true;
            }
        }

        return false;
    }

    //REST API
    @GetMapping("/listarRest")
    @ResponseBody
    public List<Customer> listarRest() {
        return customerService.findAll();
    }

}
