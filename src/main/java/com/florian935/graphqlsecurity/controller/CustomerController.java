package com.florian935.graphqlsecurity.controller;

import com.florian935.graphqlsecurity.graphql.Customer;
import com.florian935.graphqlsecurity.service.CustomerService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Mono;

@Controller
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PreAuthorize("hasRole('USER')")
    @QueryMapping
    Mono<Customer> findCustomerById(@Argument Integer customerId) {
        return this.customerService.findCustomerById(customerId);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @MutationMapping
    Mono<Customer> saveCustomer(@Argument String name) {
        return this.customerService.saveCustomer(name);
    }
}
