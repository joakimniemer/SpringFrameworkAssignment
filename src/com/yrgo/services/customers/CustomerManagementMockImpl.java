package com.yrgo.services.customers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.yrgo.domain.Call;
import com.yrgo.domain.Customer;

public class CustomerManagementMockImpl implements CustomerManagementService {
    private HashMap<String, Customer> customerMap;

    public CustomerManagementMockImpl() {
        customerMap = new HashMap<String, Customer>();
        customerMap.put("OB74", new Customer("OB74", "Fargo Ltd", "some notes"));
        customerMap.put("NV10", new Customer("NV10", "North Ltd", "some other notes"));
        customerMap.put("RM210", new Customer("RM210", "River Ltd", "some more notes"));
    }


    @Override
    public void newCustomer(Customer newCustomer) {
        customerMap.put(newCustomer.getCustomerId(), newCustomer);
    }

    @Override
    public void updateCustomer(Customer changedCustomer) {
        Customer customerToUpdate = customerMap.get(changedCustomer.getCustomerId());

        customerToUpdate.setCompanyName(changedCustomer.getCompanyName());
        customerToUpdate.setTelephone(changedCustomer.getTelephone());
        customerToUpdate.setEmail(changedCustomer.getEmail());
        customerToUpdate.setCalls(changedCustomer.getCalls());
        customerToUpdate.setNotes(changedCustomer.getNotes());
    }

    @Override
    public void deleteCustomer(Customer oldCustomer) {
        customerMap.remove(oldCustomer.getCustomerId());
    }

    @Override
    public Customer findCustomerById(String customerId) throws CustomerNotFoundException {
        return customerMap.get(customerId);
    }

    @Override
    public List<Customer> findCustomersByName(String name) {
        List<Customer> result = new ArrayList<Customer>();
        for (Customer customer : customerMap.values()) {
            if (customer.getCompanyName().equals(name)) {
                result.add(customer);
            }
        }
        return result;
    }

    @Override
    public List<Customer> getAllCustomers() {
        ArrayList<Customer> allCustomers = new ArrayList<>(customerMap.values());
        return allCustomers;
    }

    @Override
    public Customer getFullCustomerDetail(String customerId) throws CustomerNotFoundException {
        Customer customer = customerMap.get(customerId);
        System.out.println(customer.getCustomerId());
        System.out.println(customer.getCompanyName());
        System.out.println(customer.getEmail());
        System.out.println(customer.getTelephone());
        System.out.println(customer.getNotes());
        System.out.println(customer.getCalls());

        return customer;
    }

    @Override
    public void recordCall(String customerId, Call callDetails) throws CustomerNotFoundException {
        Customer customer = customerMap.get(customerId);
        customer.addCall(callDetails);
    }

}
