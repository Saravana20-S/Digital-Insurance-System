package com.insurance.auth.service;

import com.insurance.auth.dto.customer.CustomerRequest;
import com.insurance.auth.dto.customer.CustomerResponse;
import com.insurance.auth.entity.Customer;
import com.insurance.auth.entity.User;
import com.insurance.auth.mapper.CustomerMapper;
import com.insurance.auth.repository.CustomerRepository;
import com.insurance.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final UserRepository userRepository;
    private final CustomerMapper customerMapper;

    /**
     * Create customer
     */
    public CustomerResponse createCustomer(CustomerRequest request) {

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "User not found: " + request.getUserId()));

        Customer customer = Customer.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .dateOfBirth(request.getDateOfBirth())
                .phoneNumber(request.getPhoneNumber())
                .address(request.getAddress())
                .user(user)
                .build();

        return customerMapper.toResponse(customerRepository.save(customer));
    }

    /**
     * Get all customers
     */
    public List<CustomerResponse> getAllCustomers() {

        return customerRepository.findAll()
                .stream()
                .map(customerMapper::toResponse)
                .toList();
    }

    /**
     * Get customer by id
     */
    @Cacheable(value = "customers", key = "#id")
    public CustomerResponse getCustomerById(Long id) {

        Customer customer = customerRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Customer not found: " + id));

        return customerMapper.toResponse(customer);
    }

    /**
     * Update customer
     */
    @CachePut(value = "customers", key = "#id")
    public CustomerResponse updateCustomer(
            Long id,
            CustomerRequest request) {

        Customer existing = customerRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Customer not found: " + id));

        existing.setFirstName(request.getFirstName());
        existing.setLastName(request.getLastName());
        existing.setDateOfBirth(request.getDateOfBirth());
        existing.setPhoneNumber(request.getPhoneNumber());
        existing.setAddress(request.getAddress());

        Customer updated = customerRepository.save(existing);

        return customerMapper.toResponse(updated);
    }

    /**
     * Delete customer
     */
    @CacheEvict(value = "customers", key = "#id")
    public void deleteCustomer(Long id) {

        if (!customerRepository.existsById(id)) {
            throw new IllegalArgumentException(
                    "Customer not found: " + id);
        }

        customerRepository.deleteById(id);
    }
}