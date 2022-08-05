package com.bankapp;

import com.bankapp.dtos.BankAccountDTO;
import com.bankapp.dtos.CurrentBankAccountDTO;
import com.bankapp.dtos.CustomerDTO;
import com.bankapp.dtos.SavingBankAccountDTO;

import com.bankapp.enums.AccountStatus;
import com.bankapp.enums.OperationStatus;
import com.bankapp.exceptions.CustomerNotFoundException;

import com.bankapp.services.BankAccountService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;
import java.util.stream.Stream;

@SpringBootApplication
@CrossOrigin(origins = "http://localhost:4200/")
public class BankAppBackEndApplication {

    public static void main(String[] args) {
        SpringApplication.run(BankAppBackEndApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(BankAccountService bankAccountService){

        return args -> {

            Stream.of("Mathew","Jhon","Vasile").forEach(name->{
                CustomerDTO customer=new CustomerDTO();
                customer.setEmailId(name+"@gmail.com");
                customer.setRole("USER");
                customer.setPassword("1234");
                bankAccountService.saveCustomer(customer);
            });
            Stream.of("Admin","Lili","Vivi").forEach(name->{
                CustomerDTO customer=new CustomerDTO();
                customer.setEmailId(name+"@gmail.com");
                customer.setRole("ADMIN");
                customer.setPassword("1234");
                bankAccountService.saveCustomer(customer);
            });
            bankAccountService.listCustomers().forEach(customer->{
                try {
                    bankAccountService.saveCurrentBankAccount(Math.random()*90000,9000,customer.getEmailId(), AccountStatus.CREATED);
                    bankAccountService.saveSavingBankAccount(Math.random()*120000,5.5,customer.getEmailId(), AccountStatus.ACTIVATED);

                } catch (CustomerNotFoundException e) {
                    e.printStackTrace();
                }
            });
            List<BankAccountDTO> bankAccounts = bankAccountService.bankAccountList();
            for (BankAccountDTO bankAccount:bankAccounts){
                for (int i = 0; i <10 ; i++) {
                    String accountId;
                    if(bankAccount instanceof SavingBankAccountDTO){
                        accountId=((SavingBankAccountDTO) bankAccount).getAccId();
                    } else{
                        accountId=((CurrentBankAccountDTO) bankAccount).getAccId();
                    }
                    bankAccountService.credit(accountId,10000+Math.random()*120000,"Credit", OperationStatus.ACCEPTED);
                    bankAccountService.debit(accountId,1000+Math.random()*9000,"Debit", OperationStatus.PENDING);
                    bankAccountService.credit(accountId,10000+Math.random()*120000,"Credit", OperationStatus.PENDING);
                    bankAccountService.debit(accountId,1000+Math.random()*9000,"Debit", OperationStatus.ACCEPTED);
                    bankAccountService.credit(accountId,10000+Math.random()*120000,"Credit", OperationStatus.DENIED);
                    bankAccountService.debit(accountId,1000+Math.random()*9000,"Debit", OperationStatus.DENIED);
                }
            }
        };
    }
}
