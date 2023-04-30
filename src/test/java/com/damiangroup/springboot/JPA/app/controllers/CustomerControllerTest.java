package com.damiangroup.springboot.JPA.app.controllers;

import com.damiangroup.springboot.JPA.app.config.LoginSuccessHandler;
import com.damiangroup.springboot.JPA.app.models.Customer;
import com.damiangroup.springboot.JPA.app.services.CustomerService;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(CustomerController.class)
class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LoginSuccessHandler loginSuccessHandler;

    @MockBean
    private UserDetailsService userDetailsService;

    @MockBean
    private CustomerService customerService;

    public Customer createCustomer() {
        return Customer.builder()
                .id(1L)
                .name("John Doe")
                .build();
    }

    @Nested
    class CustomerDetailsTests {
        @Test
        public void testCustomerDetailsPage() throws Exception {
            Customer customer = createCustomer();
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
            Customer customer = createCustomer();
            when(customerService.findCustomerById(1L)).thenReturn(customer);
            mockMvc.perform(get("/customerDetails/{id}", 1L))
                    .andExpect(model().attribute("title", "Customer Details: John Doe"));
        }
    }

    @Nested
    class HomeTests {
        @Test
        public void testHome() throws Exception {
            Customer customer = createCustomer();
            ;
            Page<Customer> customers = new PageImpl<>(Collections.singletonList(customer));
            when(customerService.findAllCustomers(any(PageRequest.class))).thenReturn(customers);

            mockMvc.perform(get("/"))
                    .andExpect(status().isOk())
                    .andExpect(view().name("home"))
                    .andExpect(model().attributeExists("page"))
                    .andExpect(model().attributeExists("customers"));

            verify(customerService).findAllCustomers(PageRequest.of(0, 5));
        }

        @Test
        public void testHomeWithCustomPage() throws Exception {
            Customer customer = createCustomer();
            Page<Customer> customers = new PageImpl<>(Collections.singletonList(customer));
            when(customerService.findAllCustomers(any(PageRequest.class))).thenReturn(customers);

            mockMvc.perform(get("/?page=2"))
                    .andExpect(status().isOk())
                    .andExpect(view().name("home"))
                    .andExpect(model().attributeExists("page"))
                    .andExpect(model().attributeExists("customers"));

            verify(customerService).findAllCustomers(PageRequest.of(2, 5));
        }
    }

    @Nested
    class CreateCustomerForm {
        @Test
        @WithMockUser(roles = "ADMIN")
        public void testCreateCustomerForm() throws Exception {
            mockMvc.perform(get("/customerForm"))
                    .andExpect(status().isOk())
                    .andExpect(view().name("customer/customerForm"))
                    .andExpect(model().attributeExists("customer"))
                    .andExpect(model().attributeExists("title"));
        }

        @Test
        @WithMockUser(roles = "USER")
        public void testCreateCustomerFormWithUserRole() throws Exception {
            mockMvc.perform(get("/customerForm"))
                    .andExpect(status().isForbidden());
        }
    }

    @Nested
    class EditCustomerForm {
        @Test
        @WithMockUser(roles = "ADMIN")
        public void shouldEditCustomerFormAndReturnForm() throws Exception {
            Customer customer = createCustomer();

            when(customerService.findCustomerById(1L)).thenReturn(customer);

            mockMvc.perform(get("/customerForm/{id}", 1L))
                    .andExpect(status().isOk())
                    .andExpect(view().name("customer/customerForm"))
                    .andExpect(model().attributeExists("customer"))
                    .andExpect(model().attributeExists("title"));
        }

        @Test
        @WithMockUser(roles = "ADMIN")
        public void shouldRedirectToHomePageWithErrorWhenCustomerNotFound() throws Exception {
            when(customerService.findCustomerById(1L)).thenReturn(null);

            mockMvc.perform(get("/customerForm/{id}", 1L))
                    .andExpect(status().is3xxRedirection())
                    .andExpect(flash().attributeExists("error"))
                    .andExpect(flash().attribute("error", "User Not found"))
                    .andExpect(redirectedUrl("/"));
        }

        @Test
        @WithMockUser(roles = "USER")
        public void shouldNotEditCustomerFormWithUserRole() throws Exception {
            mockMvc.perform(get("/customerForm/{id}", 1L))
                    .andExpect(status().isForbidden());
        }
    }

}