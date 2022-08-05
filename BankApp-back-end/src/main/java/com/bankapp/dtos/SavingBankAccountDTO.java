package com.bankapp.dtos;
import com.bankapp.enums.AccountStatus;
import lombok.Data;
import java.util.Date;
@Data
public class SavingBankAccountDTO extends BankAccountDTO {
    private String accId;
    private double balance;
    private Date createdAt;
    private AccountStatus status;
    private CustomerDTO customerDTO;
    private double interestRate;
}
