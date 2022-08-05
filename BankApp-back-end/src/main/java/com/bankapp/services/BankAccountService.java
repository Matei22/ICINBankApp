package com.bankapp.services;

import com.bankapp.dtos.*;
import com.bankapp.entities.AccountOperation;
import com.bankapp.entities.BankAccount;
import com.bankapp.entities.TransferRequests;
import com.bankapp.enums.AccountStatus;
import com.bankapp.enums.OperationStatus;
import com.bankapp.enums.OperationType;
import com.bankapp.exceptions.BalanceNotSufficientException;
import com.bankapp.exceptions.BankAccountNotFoundException;
import com.bankapp.exceptions.CustomerNotFoundException;

import java.util.List;
public interface BankAccountService {
    BankAccount updateAccountToSuspendedOrActivated(String customerId, String accountId, AccountStatus status);
    TransferRequests UpdateTransferStatus(Long transferId, String status);
    List<TransferRequests> getAllTransferOperations(String emailId);
    List<AccountOperation> getTransferCreditOperations(String emailId);
    List<AccountOperation> getTransferDebitOperations(String emailId);
    List<BankAccount> getBankAccountsBasedByStatus(String customerId, AccountStatus status);
    CurrentBankAccountDTO newBankAccount(String bankAccountId, Double balance, String type, String emailId) throws CustomerNotFoundException;
    CustomerDTO saveCustomer(CustomerDTO customerDTO);
    CurrentBankAccountDTO saveCurrentBankAccount(double initialBalance, double overDraft, String customerId, AccountStatus accountStatus) throws CustomerNotFoundException;
    SavingBankAccountDTO saveSavingBankAccount(double initialBalance, double interestRate, String customerId, AccountStatus accountStatus) throws CustomerNotFoundException;
    List<CustomerDTO> listCustomers();
    BankAccountDTO getBankAccount(String accountId) throws BankAccountNotFoundException;
    List<AccountOperation> getAccountOperationsUnderOneCustomer(String email, List<AccountOperation> operationsByStatus);
    List<AccountOperation> getDebitOperationsWithStatusPendingUnderOneCustomer(String email);
    List<AccountOperation> getCreditOperationsWithStatusPendingUnderOneCustomer(String email);

    AccountOperation debit(String accountId, double amount, String description, OperationStatus status) throws BankAccountNotFoundException, BalanceNotSufficientException;
    AccountOperation credit(String accountId, double amount, String description, OperationStatus status) throws BankAccountNotFoundException;
    TransferRequests transfer(String accountIdSource, String accountIdDestination, double amount, OperationStatus status) throws BankAccountNotFoundException, BalanceNotSufficientException;

    List<BankAccount> bankAccountListByCustomer(String customerId);

    List<BankAccount> bankAccountListByCustomerEmail(String customerId);

    List<BankAccountDTO> bankAccountList();

    CustomerDTO getCustomer(String customerId) throws CustomerNotFoundException;

    CustomerDTO updateCustomer(CustomerDTO customerDTO);

    AccountOperation updateStatusOfAccountOperation(Long operationId, OperationStatus status, OperationType type);

    void deleteCustomer(String customerId);

    List<AccountOperationDTO> accountHistory(String accountId);

    AccountHistoryDTO getAccountHistory(String accountId, int page, int size) throws BankAccountNotFoundException;

    AccountHistoryDTO getAccountHistoryOfCustomer(String email, String accountId, int page, int size) throws BankAccountNotFoundException;

    List<CustomerDTO> searchCustomers(String keyword);
}
