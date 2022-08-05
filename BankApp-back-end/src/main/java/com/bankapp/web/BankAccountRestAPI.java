package com.bankapp.web;

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
import com.bankapp.services.BankAccountService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
public class BankAccountRestAPI {
    private BankAccountService bankAccountService;

    public BankAccountRestAPI(BankAccountService bankAccountService) {
        this.bankAccountService = bankAccountService;
    }

    @GetMapping("/accounts/{accountId}")
    public BankAccountDTO getBankAccount(@PathVariable String accountId) throws BankAccountNotFoundException {
        return bankAccountService.getBankAccount(accountId);
    }
    @GetMapping("/accounts")
    public List<BankAccountDTO> listAccounts(){
        return bankAccountService.bankAccountList();
    }
    @GetMapping("/accounts/{accountId}/operations")
    public List<AccountOperationDTO> getHistory(@PathVariable String accountId){
        return bankAccountService.accountHistory(accountId);
    }

    @GetMapping("/accounts/{accountId}/pageOperations")
    public AccountHistoryDTO getAccountHistory(
            @PathVariable String accountId,
            @RequestParam(name="page",defaultValue = "0") int page,
            @RequestParam(name="size",defaultValue = "5")int size) throws BankAccountNotFoundException {
        return bankAccountService.getAccountHistory(accountId,page,size);
    }

    @GetMapping("/customer/{email}/accounts/{accountId}/pageOperations")
    public AccountHistoryDTO getAccountHistoryOfCustomer(
            @PathVariable String email,
            @PathVariable String accountId,
            @RequestParam(name="page",defaultValue = "0") int page,
            @RequestParam(name="size",defaultValue = "5")int size) throws BankAccountNotFoundException {
        return bankAccountService.getAccountHistoryOfCustomer(email,accountId,page,size);
    }

    @GetMapping("/accounts/{customerId}")
    public List<BankAccount> getBankAccountsForCustomer(@PathVariable String customerId){
        return bankAccountService.bankAccountListByCustomer(customerId);
    }

    @GetMapping("/accounts/customer/{email}")
    public List<BankAccount> getBankAccountsForCustomerByEmail(@PathVariable String email){
        return bankAccountService.bankAccountListByCustomerEmail(email);
    }

    @GetMapping("/accounts/{email}/debit")
    public List<AccountOperation> getDebitOperationsStatusPendingByCustomer(@PathVariable String email){
        return this.bankAccountService.getDebitOperationsWithStatusPendingUnderOneCustomer(email);
    }

    @GetMapping("/accounts/{customerId}/{status}")
    public List<BankAccount> getBankAccountBasedByStatus(@PathVariable String customerId, @PathVariable AccountStatus status){
        return this.bankAccountService.getBankAccountsBasedByStatus(customerId, status);
    }

    @GetMapping("/accounts/{email}/credit")
    public List<AccountOperation> getCreditOperationsStatusPendingByCustomer(@PathVariable String email){
        return this.bankAccountService.getCreditOperationsWithStatusPendingUnderOneCustomer(email);
    }
    @PostMapping("/accounts/{operationId}/credit/accepted")
    public AccountOperation updateOperationStatusAcceptedCred(@PathVariable Long operationId){
        return this.bankAccountService.updateStatusOfAccountOperation(operationId,OperationStatus.ACCEPTED, OperationType.CREDIT);
    }
    @PostMapping("/accounts/{operationId}/credit/denied")
    public AccountOperation updateOperationStatusDeniedCred(@PathVariable Long operationId){
        return this.bankAccountService.updateStatusOfAccountOperation(operationId,OperationStatus.DENIED, OperationType.CREDIT);
    }
    @PostMapping("/accounts/{operationId}/debit/accepted")
    public AccountOperation updateOperationStatusAcceptedDeb(@PathVariable Long operationId){
        return this.bankAccountService.updateStatusOfAccountOperation(operationId,OperationStatus.ACCEPTED, OperationType.DEBIT);
    }
    @PostMapping("/accounts/{operationId}/debit/denied")
    public AccountOperation updateOperationStatusDeniedDeb(@PathVariable Long operationId){
        return this.bankAccountService.updateStatusOfAccountOperation(operationId,OperationStatus.DENIED, OperationType.DEBIT);
    }
    @PostMapping("/accounts/{transferId}/request/denied")
    public TransferRequests updateOperationStatusToDenied(@PathVariable Long transferId){
        return this.bankAccountService.UpdateTransferStatus(transferId, "DENIED");
    }
    @PostMapping("/accounts/{transferId}/request/accepted")
    public TransferRequests updateOperationStatusToAccepted(@PathVariable Long transferId){
        return this.bankAccountService.UpdateTransferStatus(transferId, "ACCEPTED");
    }

    @PostMapping("/accounts/new/{bankAccountId}/{balance}/{type}/{emailId}")
    public BankAccountDTO newBankAccount(@PathVariable String bankAccountId,
                                         @PathVariable Double balance,
                                         @PathVariable String type,
                                         @PathVariable String emailId) throws CustomerNotFoundException {
        return this.bankAccountService.newBankAccount(bankAccountId, balance, type, emailId);
    }

    @PostMapping("/accounts/debit")
    public DebitDTO debit(@RequestBody DebitDTO debitDTO) throws BankAccountNotFoundException, BalanceNotSufficientException {
        this.bankAccountService.debit(debitDTO.getAccountId(),debitDTO.getAmount(),debitDTO.getDescription(),debitDTO.getStatus());
        return debitDTO;
    }
    @PostMapping("/accounts/credit")
    public CreditDTO credit(@RequestBody CreditDTO creditDTO) throws BankAccountNotFoundException {
        this.bankAccountService.credit(creditDTO.getAccountId(),creditDTO.getAmount(),creditDTO.getDescription(), creditDTO.getStatus());
        return creditDTO;
    }
    @PostMapping("/accounts/transfer")
    public TransferRequests transfer(@RequestBody TransferRequestDTO transferRequestDTO) throws BankAccountNotFoundException, BalanceNotSufficientException {
        return this.bankAccountService.transfer(
                transferRequestDTO.getAccountSource(),
                transferRequestDTO.getAccountDestination(),
                transferRequestDTO.getAmount(),
                transferRequestDTO.getStatus());
    }

    @PostMapping("/accounts/{customerId}/{accountId}/{status}")
    public BankAccount updateStatus(@PathVariable String customerId,
                                    @PathVariable String accountId,
                                    @PathVariable AccountStatus status){
        return bankAccountService.updateAccountToSuspendedOrActivated(customerId,accountId,status);
    }

    @GetMapping("/accounts/transfer/{emailId}")
    public List<TransferRequests> getTransferRequests(@PathVariable String emailId){
        return this.bankAccountService.getAllTransferOperations(emailId);
    }
}
