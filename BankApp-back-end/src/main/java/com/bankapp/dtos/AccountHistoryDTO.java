package com.bankapp.dtos;

import com.bankapp.enums.AccountStatus;
import lombok.Data;

import java.util.List;
@Data
public class AccountHistoryDTO {
    private String accountId;
    private double balance;
    private int currentPage;
    private int totalPages;
    private int pageSize;
    private AccountStatus status;
    private List<AccountOperationDTO> accountOperationDTOS;
}
