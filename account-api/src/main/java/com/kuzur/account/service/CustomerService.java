package com.kuzur.account.service;

import com.kuzur.account.dto.CustomerDto;
import com.kuzur.account.dto.CustomerDtoConverter;
import com.kuzur.account.exception.CustomerNotFoundException;
import com.kuzur.account.model.Customer;
import com.kuzur.account.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerDtoConverter converter;

    public CustomerService(CustomerRepository customerRepository, CustomerDtoConverter converter) {
        this.customerRepository = customerRepository;
        this.converter = converter;
    }

    protected Customer findByCustomerById(String id){ //protected olmasının sebebi sadece paket içi erişim gerekli
        return customerRepository.findById(id).orElseThrow(
                () -> new CustomerNotFoundException("Customer could not find by id :" + id));
    }


    public CustomerDto getCustomerById(String customerId) {

        return converter.convertToCustomerDto(findByCustomerById(customerId));
    }

    public List<CustomerDto> getAllCustomer() {

        return customerRepository.findAll()
                .stream()
                .map(converter::convertToCustomerDto)
                .collect(Collectors.toList());
    }
}
