package com.bankapp.repositories;

import com.bankapp.entities.BankAccount;
import com.bankapp.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer,String> {
    @Query("select c from Customer c where c.emailId like :kw")
    List<Customer> searchCustomer(@Param("kw") String keyword);
    Customer findByEmailId(String email);
    Customer findByEmailIdAndPassword(String email, String password);
    Customer findByBankAccountsContaining(BankAccount bankAccount);
}
