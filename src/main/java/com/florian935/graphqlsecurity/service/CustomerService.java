package com.florian935.graphqlsecurity.service;

import com.florian935.graphqlsecurity.graphql.Customer;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class CustomerService {

    private final Map<Integer, Customer> inMemoryDb = new ConcurrentHashMap<>();
    private final AtomicInteger customerId = new AtomicInteger();


    public Mono<Customer> findCustomerById(Integer customerId) {
        final var customer = this.inMemoryDb.get(customerId);

        return Mono.just(customer);
    }

    public Mono<Customer> saveCustomer(String name) {
        final var id = this.customerId.incrementAndGet();
        final var customerToSave = new Customer(id, name);

        this.inMemoryDb.put(id, customerToSave);

        return Mono.just(customerToSave);
    }
}
