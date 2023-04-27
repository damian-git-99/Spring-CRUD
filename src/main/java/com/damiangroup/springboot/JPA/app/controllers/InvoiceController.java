package com.damiangroup.springboot.JPA.app.controllers;

import java.util.List;
import javax.validation.Valid;

import com.damiangroup.springboot.JPA.app.models.Customer;
import com.damiangroup.springboot.JPA.app.services.CustomerService;
import com.damiangroup.springboot.JPA.app.models.Invoice;
import com.damiangroup.springboot.JPA.app.models.InvoiceItem;
import com.damiangroup.springboot.JPA.app.models.Product;
import com.damiangroup.springboot.JPA.app.services.InvoiceService;
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
@RequestMapping("/invoice")
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

    @GetMapping("/invoiceForm/{customerId}")
    public String createInvoiceForm(@PathVariable(value = "customerId") Long customerId, Model model, RedirectAttributes flash) {
        Customer customer = customerService.findCustomerById(customerId);
        if (customer == null) {
            flash.addFlashAttribute("error", "User Not found: ".concat(customerId.toString()));
            return "redirect:/";
        }
        Invoice invoice = new Invoice();
        invoice.setCustomer(customer);
        model.addAttribute("invoice", invoice);
        return "invoice/form";
    }

    @PostMapping("/invoiceForm")
    public String createInvoicePost(@Valid Invoice invoice, BindingResult result,
                                    @RequestParam(name = "item_id[]", required = false) Long[] itemId,
                                    @RequestParam(name = "cantidad[]", required = false) Integer[] cantidad,
                                    RedirectAttributes flash,
                                    SessionStatus status,
                                    Model model) {

        if (result.hasErrors()) {
            model.addAttribute("title", "Create Invoice");
            model.addAttribute("invoice", invoice);
            return "invoice/form";
        }

        if (itemId == null || itemId.length == 0) {
            model.addAttribute("title", "Create Invoice");
            model.addAttribute("error", "Invoice must contain at least one item");
            return "invoice/form";
        }

        for (int i = 0; i < itemId.length; i++) {
            Product product = invoiceService.findProductById(itemId[i]);
            InvoiceItem invoiceItem = new InvoiceItem(cantidad[i], product);
            invoice.addItemFactura(invoiceItem);
        }

        invoiceService.saveInvoice(invoice);
        status.setComplete();
        flash.addFlashAttribute("success", "Invoice was created successfully");
        return "redirect:/customerDetails/" + invoice.getCustomer().getId();
    }

    @GetMapping("/invoiceDetails/{id}")
    public String invoiceDetails(@PathVariable(value = "id") Long id, Model model, RedirectAttributes flash) {
        Invoice invoice = invoiceService.findInvoiceById(id);
        if (invoice == null) {
            flash.addFlashAttribute("error", "Invoice not found");
            return "redirect:/";
        }

        model.addAttribute("invoice", invoice);
        model.addAttribute("title", "Invoice : ".concat(invoice.getDescription()));
        return "invoice/invoiceDetails";
    }

    @GetMapping("/deleteInvoice/{id}")
    public String deleteInvoice(@PathVariable(value = "id") Long id, RedirectAttributes flash) {
        Invoice invoice = invoiceService.findInvoiceById(id);
        if (invoice == null) {
            flash.addFlashAttribute("error", "Invoice not found");
            return "redirect:/";
        }

        invoiceService.deleteInvoiceById(id);
        flash.addFlashAttribute("success", "Invoice deleted successfully");
        return "redirect:/customerDetails/" + invoice.getId();
    }

    @GetMapping(value = "/products/{term}", produces = {"application/json"})
    @ResponseBody
    public List<Product> findProducts(@PathVariable(value = "term") String term) {
        return invoiceService.findProductByProductName(term);
    }

}
