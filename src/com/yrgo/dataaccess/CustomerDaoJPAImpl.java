package com.yrgo.dataaccess;

import com.yrgo.domain.Call;
import com.yrgo.domain.Customer;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class CustomerDaoJPAImpl implements CustomerDao {

    @PersistenceContext
    private EntityManager em;


    @Override
    public void create(Customer customer) {
        em.persist(customer);
    }

    @Override
    public Customer getById(String customerId) throws RecordNotFoundException {
        try {
            return (Customer) em.createQuery("select customer from Customer as customer where Customer.customerId=:customerId").setParameter("customerId", customerId).getSingleResult();
        } catch (javax.persistence.NoResultException e) {
            throw new RecordNotFoundException();
        }

    }

    @Override
    public List<Customer> getByName(String name) throws RecordNotFoundException {
        List<Customer> list = em.createQuery("select customer from Customer as customer where Customer.companyName=:name", Customer.class).setParameter("name", name).getResultList();
        if (list.size() == 0){
            throw new RecordNotFoundException();
        }
        return list;
    }

    @Override
    public void update(Customer customerToUpdate) throws RecordNotFoundException {
        Customer customer = em.find(Customer.class, customerToUpdate.getCustomerId());
        if (customer == null){
            throw new RecordNotFoundException();
        }
        customer.setCompanyName(customerToUpdate.getCompanyName());
        customer.setEmail(customerToUpdate.getEmail());
        customer.setTelephone(customerToUpdate.getTelephone());
        customer.setNotes(customerToUpdate.getNotes());
        em.merge(customer);
    }

    @Override
    public void delete(Customer oldCustomer) throws RecordNotFoundException {
        Customer customer = em.find(Customer.class, oldCustomer.getCustomerId());
        if (customer == null){
            throw new RecordNotFoundException();
        }
        em.remove(customer);
    }

    @Override
    public List<Customer> getAllCustomers() {
        return em.createQuery("select customer from Customer as customer", Customer.class).getResultList();
    }

    @Override
    public Customer getFullCustomerDetail(String customerId) throws RecordNotFoundException {
        Customer customer = em.find(Customer.class, customerId);
        if (customer == null) {
            throw new RecordNotFoundException();
        }
        List<Call> calls = em.createQuery("select call from Call as call where Call.callerId=:customerId", Call.class).setParameter("customerId", customerId).getResultList();
        customer.setCalls(calls);
        return customer;
    }

    @Override
    public void addCall(Call newCall, String customerId) throws RecordNotFoundException {
        Customer customer = em.find(Customer.class, customerId);
        if (customer == null) {
            throw new RecordNotFoundException();
        }
        List<Call> list = customer.getCalls();
        list.add(newCall);
        customer.setCalls(list);
    }

}
