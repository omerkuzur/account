package com.kuzur.account.service;

import com.kuzur.account.TestSupport;
import com.kuzur.account.dto.CustomerDto;
import com.kuzur.account.dto.CustomerDtoConverter;
import com.kuzur.account.exception.CustomerNotFoundException;
import com.kuzur.account.model.Customer;
import com.kuzur.account.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

class CustomerServiceTest {

    private CustomerService service;
    private CustomerRepository customerRepository;
    private CustomerDtoConverter converter;

    @BeforeEach
    public void setUp(){
        customerRepository = mock(CustomerRepository.class);
        converter = mock(CustomerDtoConverter.class);
        service = new CustomerService(customerRepository, converter);

    }

    @Test
    public void testFindByCustomerId_whenCustomerIdExists_shouldReturnCustomer(){
        Customer customer =new Customer("id", "name", "surname", Set.of());

        Mockito.when(customerRepository.findById("id")).thenReturn(Optional.of(customer));

        Customer result = service.findByCustomerById("id");

        assertEquals(result,
                customer);

    }

    @Test
    public void testFindByCustomerById_whenCustomerIdDoesNotExists_shouldThrowCustomerNotFoundException() {

        Mockito.when(customerRepository.findById("id")).thenReturn(Optional.empty());

        assertThrows(CustomerNotFoundException.class, () -> service.findByCustomerById("id"));
    }



    @Test
    public void testGetCustomerById_whenCustomerIdExists_shouldReturnCustomer(){
        Customer customer = generateCustomer();

        Mockito.when(customerRepository.findById("customer-id")).thenReturn(Optional.of(customer));

        Customer result = service.findByCustomerById("customer-id");

        assertEquals(result,
                customer);
    }

    private Customer generateCustomer() {
        return new Customer("customer-id", "customer-name", "customer-surname", Set.of());
    }

    @Test
    public void testGetCustomerById_whenCustomerIdDoesNotExists_shouldThrowCustomerNotFoundException(){

        Mockito.when(customerRepository.findById("id")).thenReturn(Optional.empty());

        assertThrows(CustomerNotFoundException.class,
                () -> service.getCustomerById("id"));

        Mockito.verifyNoInteractions(converter);

    }
}