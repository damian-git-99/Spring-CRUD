package com.damiangroup.springboot.JPA.app.controllers;

import java.io.IOException;

import javax.validation.Valid;

import com.damiangroup.springboot.JPA.app.services.CustomerService;
import com.damiangroup.springboot.JPA.app.models.Customer;
import com.damiangroup.springboot.JPA.app.services.UploadFileService;
import com.damiangroup.springboot.JPA.app.util.paginator.PageRender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.annotation.Secured;
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
    private final UploadFileService uploadFile;

    @Autowired
    public CustomerController(CustomerService customerService, UploadFileService uploadFile) {
        this.customerService = customerService;
        this.uploadFile = uploadFile;
    }

    @GetMapping("/customerDetails/{id}")
    public String customerDetails(@PathVariable(value = "id") Long id, Model model, RedirectAttributes flash) {
        Customer customer = customerService.findCustomerById(id);
        if (customer == null) {
            flash.addFlashAttribute("error", "Customer does not exist");
            return "redirect:/";
        }
        model.addAttribute("customer", customer);
        model.addAttribute("title", "Customer Details: " + customer.getName());
        return "customerDetails";
    }

    @GetMapping("/")
    public String home(@RequestParam(name = "page", defaultValue = "0") int page, Model model) {
        Pageable pageRequest = PageRequest.of(page, 5);
        Page<Customer> customers = customerService.findAllCustomers(pageRequest);
        PageRender<Customer> pageRender = new PageRender<>("/", customers);

        model.addAttribute("page", pageRender);
        model.addAttribute("customers", customers);
        return "home";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/customerForm")
    public String createCustomerForm(Model model) {
        Customer customer = new Customer();
        model.addAttribute("customer", customer);
        model.addAttribute("title", "Customer Form");
        return "customerForm";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/customerForm/{id}")
    public String editCustomerForm(@PathVariable(value = "id") Long id, Model model, RedirectAttributes flash) {
        Customer customer = customerService.findCustomerById(id);
        if (customer == null) {
            flash.addFlashAttribute("error", "User Not found");
            return "redirect:/";
        }
        model.addAttribute("title", "Edit Customer");
        model.addAttribute("customer", customer);
        return "customerForm";
    }

    @Secured("ROLE_ADMIN")
    @PostMapping("/customerForm")
    public String createOrEditCustomer(@Valid Customer customer, BindingResult result, Model model, RedirectAttributes flash,
                                       @RequestParam("file") MultipartFile foto, SessionStatus status) {
        System.out.println(foto);
        String urlFoto;
        try {
            urlFoto = uploadFile.savePhoto(foto, customer);
        } catch (IOException e) {
            e.printStackTrace();
            urlFoto = "";
        }
        customer.setPhoto(urlFoto);

        if (result.hasErrors()) {
            model.addAttribute("customer", customer);
            return "customerForm";
        }
        customerService.saveCustomer(customer);
        flash.addFlashAttribute("success", "Customer created successfully");
        status.setComplete();
        return "redirect:/";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/deleteCustomer/{id}")
    public String deleteCustomerById(@PathVariable(value = "id") Long id, RedirectAttributes flash) {
        Customer customer = customerService.findCustomerById(id);

        if (customer == null) {
            flash.addFlashAttribute("error", "User Not found: ".concat(id.toString()));
            return "redirect:/";
        }

        uploadFile.deletePhoto(customer); // todo move this to the service layer
        customerService.deleteCustomerById(id);
        flash.addFlashAttribute("success", "Customer deleted successfully");
        return "redirect:/";
    }

}
