package com.damiangroup.springboot.JPA.app.controllers;

import com.damiangroup.springboot.JPA.app.config.LoginSuccessHandler;
import com.damiangroup.springboot.JPA.app.config.SpringSecurityConfig;
import com.damiangroup.springboot.JPA.app.models.Customer;
import com.damiangroup.springboot.JPA.app.services.CustomerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(CustomerController.class)
@Import(SpringSecurityConfig.class)
class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LoginSuccessHandler loginSuccessHandler;

    @MockBean
    private UserDetailsService userDetailsService;

    @MockBean
    private CustomerService customerService;

    @Test
    public void testCustomerDetailsPage() throws Exception {
        Customer customer = new Customer();
        when(customerService.findCustomerById(1L)).thenReturn(customer);
        mockMvc.perform(get("/customerDetails/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(view().name("customer/customerDetails"))
                .andExpect(model().attributeExists("customer"))
                .andExpect(model().attributeExists("title"));
    }

    @Test
    public void testNonExistentCustomer() throws Exception {
        mockMvc.perform(get("/customerDetails/{id}", -1L))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"))
                .andExpect(flash().attributeExists("error"));
    }

    @Test
    public void testNonExistentCustomerErrorMessage() throws Exception {
        mockMvc.perform(get("/customerDetails/{id}", -1L))
                .andExpect(flash().attribute("error", "Customer does not exist"));
    }

    @Test
    public void testCustomerDetailsName() throws Exception {
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setName("John Doe");
        when(customerService.findCustomerById(1L)).thenReturn(customer);

        mockMvc.perform(get("/customerDetails/{id}", 1L))
                .andExpect(model().attribute("title", "Customer Details: John Doe"));
    }

}