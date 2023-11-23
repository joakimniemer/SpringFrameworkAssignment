package com.yrgo.test;

import com.yrgo.domain.Customer;
import com.yrgo.services.customers.CustomerManagementService;
import com.yrgo.services.customers.CustomerNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration({"/other-tiers.xml", "/datasource-test.xml"})
@Transactional
public class TestCustomerManagementServiceProductionImpl {

    @Autowired
    private CustomerManagementService customerService;

    @Test
    void createNewCustomerTest() {
        Customer customer = new Customer("CS03939", "Acme", "Good Customer");
        customerService.newCustomer(customer);
        Customer customer2 = new Customer("hej123", "AV AB", "Customer");
        customerService.newCustomer(customer2);
        int size = customerService.getAllCustomers().size();

        assertTrue(size == 2);
    }

    @Test
    void findCustomerTest() {
        Customer customer = new Customer("CS03939", "Acme", "Good Customer");
        customerService.newCustomer(customer);
        Customer getCustomer = null;
        try {
            getCustomer = customerService.findCustomerById("CS03939");
        } catch (CustomerNotFoundException e) {
            throw new RuntimeException(e);
        }
        String test = "";
        assertTrue(customer.equals(getCustomer));
    }


}

