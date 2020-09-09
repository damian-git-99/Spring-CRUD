package com.damiangroup.springboot.JPA.app.controllers;

import javax.validation.Valid;

import com.damiangroup.springboot.JPA.app.controllers.util.paginator.PageRender;
import com.damiangroup.springboot.JPA.app.models.entity.Cliente;
import com.damiangroup.springboot.JPA.app.models.service.IClienteService;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ClienteController {

    @Autowired
    private IClienteService clienteService;

    @GetMapping("/listar")
    public String listar(@RequestParam(name = "page", defaultValue = "0") int page, Model model) {
        Pageable pageRequest = PageRequest.of(page, 5);
        // Page es un iterable
        Page<Cliente> clientes = clienteService.findAll(pageRequest);
        PageRender<Cliente> pageRender = new PageRender<>("/listar", clientes);
        System.out.println(clientes.getTotalPages());
        model.addAttribute("page", pageRender);
        model.addAttribute("titulo", "listado de clientes");
        model.addAttribute("clientes", clientes);
        return "listar";
    }

    @GetMapping("/form")
    public String crear(Model model) {
        Cliente cliente = new Cliente();
        model.addAttribute("cliente", cliente);
        model.addAttribute("titulo", "Formulario de Cliente");
        return "form";
    }

    @PostMapping("/form")
    public String guardar(@Valid Cliente cliente, BindingResult result, Model model, RedirectAttributes flash) {
        if (result.hasErrors()) {
            model.addAttribute("cliente", cliente);
            return "/form";
        }
        clienteService.save(cliente);
        flash.addFlashAttribute("success", "Cliente Creado o actualizado con exito");
        return "redirect:listar";
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

        model.addAttribute("titulo", "Formulario de Cliente (Update)");
        model.addAttribute("cliente", cliente);
        return "form";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable(value = "id") Long id, Model model, RedirectAttributes flash) {
        if (id > 0)
            clienteService.delete(id);
        flash.addFlashAttribute("success", "Cliente eliminado con exito");
        return "redirect:/listar";
    }

}
