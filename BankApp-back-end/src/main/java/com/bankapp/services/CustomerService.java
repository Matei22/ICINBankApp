package com.bankapp.services;

import com.bankapp.entities.Customer;
import com.bankapp.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository repo;
    public Customer saveUser(Customer customer){
        return repo.save(customer);
    }
    public Customer fetchUserByEmailId(String email){
        return repo.findByEmailId(email);
    }
    public Customer fetchUserByEmailIdAndPassword(String email, String password){
        return repo.findByEmailIdAndPassword(email, password);
    }
    public Customer fetchUserByEmailAndPasswordAndRole(String email, String password){
        Customer customer = repo.findByEmailId(email);
        System.out.println(customer.toString());
        if(!Objects.equals(customer.getPassword(), password)){
            return null;
        }
        else return customer;
    }

}
