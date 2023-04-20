package com.damiangroup.springboot.JPA.app.controllers;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.damiangroup.springboot.JPA.app.models.entity.Cliente;
import com.damiangroup.springboot.JPA.app.models.service.IClienteService;
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

    @Autowired
    private IClienteService clienteService;

    @Autowired
    private IUploadFileService uploadFile;

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
    @GetMapping({"/listar", "/"})
    public String listar(@RequestParam(name = "page", defaultValue = "0") int page, Model model,
                         Authentication authentication, HttpServletRequest request) {
        Pageable pageRequest = PageRequest.of(page, 5);

        if (authentication != null) {
            // Hacer algo con el usuaruio
        }

        //Manera programatica
        if (hasRole("ROLE_ADMIN")) System.err.println("Rol ADMIN!!!");
        else System.err.println("Rol Normal!!!");

        //Con spring
        SecurityContextHolderAwareRequestWrapper securityContext = new SecurityContextHolderAwareRequestWrapper(request, "ROLE_");
        if (securityContext.isUserInRole("ADMIN")) System.err.println("Rol ADMIN (securityContext)!!!");
        else System.err.println("Rol Normal (securityContext)!!!");


        // Obtener authentication de forma estatica
        // Authentication auth= SecurityContextHolder.getContext().getAuthentication();

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
    @Secured("ROLE_ADMIN")
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
    @Secured("ROLE_ADMIN")
    @PostMapping("/form")
    public String guardar(@Valid Cliente cliente, BindingResult result, Model model, RedirectAttributes flash,
                          @RequestParam("file") MultipartFile foto, SessionStatus status) {

        String urlFoto;
        try {
            urlFoto = uploadFile.guardarFoto(foto, cliente);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            urlFoto = "";
        }
        cliente.setFoto(urlFoto);

        if (result.hasErrors()) {
            model.addAttribute("cliente", cliente);
            return "/form";
        }

        clienteService.save(cliente);
        flash.addFlashAttribute("info", "Imagen subida correctamente");
        flash.addFlashAttribute("success", "Cliente Creado o actualizado con exito");
        status.setComplete();
        return "redirect:/listar";
    }

    @Secured("ROLE_ADMIN")
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

    @Secured("ROLE_ADMIN")
    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable(value = "id") Long id, Model model, RedirectAttributes flash) {

        if (id > 0) {
            Cliente cliente = clienteService.findOne(id);
            if (cliente == null) {
                flash.addFlashAttribute("error", "No existe un cliente con el id: ".concat(id.toString()));
                return "redirect:/listar";
            }
            if (uploadFile.eliminarFoto(cliente)) {
                flash.addFlashAttribute("success", "Imagen Borrada: ".concat(cliente.getFoto()));
            } else
                flash.addFlashAttribute("error",
                        "La imagen no se pudo borrar: (El cliente no tiene imagen o hubo un error al intentar borrarla)"
                                .concat(cliente.getFoto()));
            clienteService.delete(id);
        }

        flash.addFlashAttribute("success", "Cliente eliminado con exito");
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
    public List<Cliente> listarRest() {
        ;
        return clienteService.findAll();
    }

}
