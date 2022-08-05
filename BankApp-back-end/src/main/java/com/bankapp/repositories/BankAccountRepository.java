package com.bankapp.repositories;

import com.bankapp.entities.AccountOperation;
import com.bankapp.entities.BankAccount;
import com.bankapp.entities.Customer;
import com.bankapp.enums.AccountStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface BankAccountRepository extends JpaRepository<BankAccount,String> {
    List<BankAccount> findByCustomer(Customer customer);
    BankAccount findByAccId(String bankAccountId);
    List<BankAccount> findBankAccountsByStatusAndCustomer(AccountStatus status, Customer customer);
    BankAccount findByAccountOperationsContaining(AccountOperation accountOperation);

}
