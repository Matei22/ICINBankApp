package com.bankapp.services;

import com.bankapp.dtos.*;
import com.bankapp.entities.*;
import com.bankapp.enums.AccountStatus;
import com.bankapp.enums.OperationStatus;
import com.bankapp.enums.OperationType;
import com.bankapp.exceptions.BalanceNotSufficientException;
import com.bankapp.exceptions.BankAccountNotFoundException;
import com.bankapp.exceptions.CustomerNotFoundException;
import com.bankapp.mappers.BankAccountMapperImpl;
import com.bankapp.repositories.AccountOperationRepository;
import com.bankapp.repositories.BankAccountRepository;
import com.bankapp.repositories.CustomerRepository;
import com.bankapp.repositories.TransferRequestsRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class BankAccountServiceImpl implements BankAccountService {
    private CustomerRepository customerRepository;
    private BankAccountRepository bankAccountRepository;
    private AccountOperationRepository accountOperationRepository;
    private BankAccountMapperImpl dtoMapper;
    private TransferRequestsRepository transferRequestsRepository;

    @Override
    public CustomerDTO saveCustomer(CustomerDTO customerDTO) {
        log.info("Saving new Customer");
        Customer customer=dtoMapper.fromCustomerDTO(customerDTO);
        Customer savedCustomer = customerRepository.save(customer);
        return dtoMapper.fromCustomer(savedCustomer);
    }
    @Override
    public CurrentBankAccountDTO saveCurrentBankAccount(double initialBalance, double overDraft, String customerId, AccountStatus accountStatus) throws CustomerNotFoundException {
        Customer customer = null;
        customer=customerRepository.findByEmailId(customerId);
        if(customer==null)
            throw new CustomerNotFoundException("Customer not found");
        CurrentAccount currentAccount=new CurrentAccount();
        currentAccount.setAccId(UUID.randomUUID().toString());
        currentAccount.setCreatedAt(new Date());
        currentAccount.setBalance(initialBalance);
        currentAccount.setOverDraft(overDraft);
        currentAccount.setCustomer(customer);
        currentAccount.setStatus(accountStatus);
        CurrentAccount savedBankAccount = bankAccountRepository.save(currentAccount);
        return dtoMapper.fromCurrentBankAccount(savedBankAccount);
    }
    @Override
    public SavingBankAccountDTO saveSavingBankAccount(double initialBalance, double interestRate, String customerId, AccountStatus accountStatus) throws CustomerNotFoundException {
        Customer customer;
        customer=customerRepository.findByEmailId(customerId);
        if(customer==null)
            throw new CustomerNotFoundException("Customer not found");
        SavingAccount savingAccount=new SavingAccount();
        savingAccount.setAccId(UUID.randomUUID().toString());
        savingAccount.setCreatedAt(new Date());
        savingAccount.setBalance(initialBalance);
        savingAccount.setInterestRate(interestRate);
        savingAccount.setCustomer(customer);
        savingAccount.setStatus(accountStatus);
        SavingAccount savedBankAccount = bankAccountRepository.save(savingAccount);
        return dtoMapper.fromSavingBankAccount(savedBankAccount);
    }
    @Override
    public List<CustomerDTO> listCustomers() {
        List<Customer> customers = customerRepository.findAll();
        List<CustomerDTO> customerDTOS = customers.stream()
                .map(customer -> dtoMapper.fromCustomer(customer))
                .collect(Collectors.toList());
        return customerDTOS;
    }
    @Override
    public BankAccountDTO getBankAccount(String accountId) throws BankAccountNotFoundException {
        BankAccount bankAccount=bankAccountRepository.findById(accountId)
                .orElseThrow(()->new BankAccountNotFoundException("BankAccount not found"));
        if(bankAccount instanceof SavingAccount){
            SavingAccount savingAccount= (SavingAccount) bankAccount;
            return dtoMapper.fromSavingBankAccount(savingAccount);
        } else {
            CurrentAccount currentAccount= (CurrentAccount) bankAccount;
            return dtoMapper.fromCurrentBankAccount(currentAccount);
        }
    }
    @Override
    public AccountOperation debit(String accountId, double amount, String description, OperationStatus status) throws BankAccountNotFoundException, BalanceNotSufficientException {
        BankAccount bankAccount=bankAccountRepository.findById(accountId)
                .orElseThrow(()->new BankAccountNotFoundException("BankAccount not found"));
        if(bankAccount.getBalance()<amount)
            throw new BalanceNotSufficientException("Balance not sufficient");
        AccountOperation accountOperation=new AccountOperation();
        accountOperation.setType(OperationType.DEBIT);
        accountOperation.setAmount(amount);
        accountOperation.setDescription(description);
        accountOperation.setOperationDate(new Date());
        accountOperation.setBankAccount(bankAccount);
        accountOperation.setStatus(status);
        accountOperationRepository.save(accountOperation);
        bankAccount.setBalance(bankAccount.getBalance()-amount);
        bankAccountRepository.save(bankAccount);
        return accountOperation;

    }
    @Override
    public AccountOperation credit(String accountId, double amount, String description, OperationStatus status) throws BankAccountNotFoundException {
        BankAccount bankAccount=bankAccountRepository.findById(accountId)
                .orElseThrow(()->new BankAccountNotFoundException("BankAccount not found"));
        AccountOperation accountOperation=new AccountOperation();
        accountOperation.setType(OperationType.CREDIT);
        accountOperation.setAmount(amount);
        accountOperation.setDescription(description);
        accountOperation.setOperationDate(new Date());
        accountOperation.setBankAccount(bankAccount);
        accountOperation.setStatus(status);
        accountOperationRepository.save(accountOperation);
        bankAccount.setBalance(bankAccount.getBalance()+amount);
        bankAccountRepository.save(bankAccount);
        return accountOperation;
    }
    @Override
    public TransferRequests transfer(String accountIdSource, String accountIdDestination, double amount, OperationStatus status) throws BankAccountNotFoundException, BalanceNotSufficientException {
        AccountOperation debitOperation = debit(accountIdSource,amount,"Transfer to "+accountIdDestination,status);
        AccountOperation creditOperation = credit(accountIdDestination,amount,"Transfer from "+accountIdSource, status);
        TransferRequests returnedData = new TransferRequests();
        returnedData.setAmount(creditOperation.getAmount());

        BankAccount debitBankAccount = bankAccountRepository.findByAccountOperationsContaining(debitOperation);
        BankAccount creditBankAccount = bankAccountRepository.findByAccountOperationsContaining(creditOperation);

        Customer customerFrom = debitBankAccount.getCustomer();
        Customer customerTo = creditBankAccount.getCustomer();
        returnedData.setEmailIdFrom(customerFrom.getEmailId());
        returnedData.setEmailIdTo(customerTo.getEmailId());
        returnedData.setCreditId(creditOperation.getOperationId());
        returnedData.setDebitId(debitOperation.getOperationId());
        returnedData.setStatus("PENDING");
        System.out.println(returnedData.getStatus());
        return transferRequestsRepository.save(returnedData);
    }
    @Override
    public List<TransferRequests> getAllTransferOperations(String emailId){
        return transferRequestsRepository.getTransferRequestsByEmailIdFromAndStatus(emailId, "PENDING");

    }
    @Override
    public List<AccountOperation> getTransferDebitOperations(String emailId){
        List<TransferRequests> transferRequestsByCustomer = transferRequestsRepository.getTransferRequestsByEmailIdFromAndStatus(emailId, "PENDING");
        List<AccountOperation> debitOperations = new ArrayList<>();
        for(TransferRequests transferRequests:transferRequestsByCustomer)
        {
            Long debitId = transferRequests.getDebitId();
            debitOperations.add(accountOperationRepository.findByOperationId(debitId));
        }
        return debitOperations;
    }
    @Override
    public List<AccountOperation> getTransferCreditOperations(String emailId){
        List<TransferRequests> transferRequestsByCustomer = transferRequestsRepository.getTransferRequestsByEmailIdFromAndStatus(emailId, "PENDING");
        List<AccountOperation> debitOperations = new ArrayList<>();
        for(TransferRequests transferRequests:transferRequestsByCustomer)
        {
            Long debitId = transferRequests.getCreditId();
            debitOperations.add(accountOperationRepository.findByOperationId(debitId));
        }
        return debitOperations;
    }
    @Override
    public List<AccountOperation> getCreditOperationsWithStatusPendingUnderOneCustomer(String email){
        List<AccountOperation> operationsByStatus =accountOperationRepository.findByTypeAndStatus( OperationType.CREDIT, OperationStatus.PENDING);
        List<AccountOperation> creditOperationsUnderTransfer = getTransferCreditOperations(email);
        for(AccountOperation c: creditOperationsUnderTransfer){
            operationsByStatus.remove(c);
        }
        return getAccountOperationsUnderOneCustomer(email, operationsByStatus);
    }
    @Override
    public List<AccountOperation> getDebitOperationsWithStatusPendingUnderOneCustomer(String email){
        List<AccountOperation> operationsByStatus = accountOperationRepository.findByTypeAndStatus( OperationType.DEBIT, OperationStatus.PENDING);
        List<AccountOperation> debitOperationsUnderTransfer = getTransferDebitOperations(email);
        for(AccountOperation c: debitOperationsUnderTransfer){
            operationsByStatus.remove(c);
        }
        return getAccountOperationsUnderOneCustomer(email, operationsByStatus);
    }
    @Override
    public List<BankAccountDTO> bankAccountList(){
        List<BankAccount> bankAccounts = bankAccountRepository.findAll();
        return bankAccounts.stream().map(bankAccount -> {
            if (bankAccount instanceof SavingAccount savingAccount) {
                return dtoMapper.fromSavingBankAccount(savingAccount);
            } else {
                CurrentAccount currentAccount = (CurrentAccount) bankAccount;
                return dtoMapper.fromCurrentBankAccount(currentAccount);
            }
        }).collect(Collectors.toList());
    }
    @Override
    public CurrentBankAccountDTO newBankAccount(String bankAccountId, Double balance, String type, String emailId) throws CustomerNotFoundException {
        CustomerDTO customerDTO = getCustomer(emailId);
        List<BankAccount> bankAccounts = bankAccountRepository.findAll();

        for(BankAccount b:bankAccounts){
            System.out.println(Objects.equals(b.getAccId(), bankAccountId));

            if(Objects.equals(b.getAccId(), bankAccountId)) {
                System.out.println(Objects.equals(b.getAccId(), bankAccountId));
                throw new Error("Bank account already exist");
            }
        }
        CurrentBankAccountDTO accountDTO = new CurrentBankAccountDTO();
        accountDTO.setAccId(bankAccountId);
        accountDTO.setBalance(balance);
        accountDTO.setType(type);
        accountDTO.setStatus(AccountStatus.CREATED);
        accountDTO.setCustomerDTO(customerDTO);
        accountDTO.setCreatedAt(new Date());
        BankAccount bankAccount = dtoMapper.fromCurrentBankAccountDTO(accountDTO);
        bankAccountRepository.save(bankAccount);

        return accountDTO;

    }
    @Override
    public CustomerDTO getCustomer(String customerId) throws CustomerNotFoundException {
        Customer customer = customerRepository.findByEmailId(customerId);
        return dtoMapper.fromCustomer(customer);
    }
    @Override
    public CustomerDTO updateCustomer(CustomerDTO customerDTO) {
        log.info("Saving new Customer");
        Customer customer=dtoMapper.fromCustomerDTO(customerDTO);
        Customer savedCustomer = customerRepository.save(customer);
        return dtoMapper.fromCustomer(savedCustomer);
    }
    @Override
    public AccountOperation updateStatusOfAccountOperation(Long operationId, OperationStatus status, OperationType type){
        AccountOperation accountOperation = accountOperationRepository.findByOperationId(operationId);
        if(status == OperationStatus.DENIED && type == OperationType.DEBIT){
            AccountOperation account = accountOperationRepository.findByOperationId(operationId);
            double rest = account.getAmount();
            double currentBalance = bankAccountRepository.findByAccountOperationsContaining(account).getBalance();
            bankAccountRepository.findByAccountOperationsContaining(account).setBalance(currentBalance + rest);
            accountOperation.setStatus(status);}
        else if(status == OperationStatus.DENIED && type == OperationType.CREDIT){
            AccountOperation account = accountOperationRepository.findByOperationId(operationId);
            double rest = account.getAmount();
            double currentBalance = bankAccountRepository.findByAccountOperationsContaining(account).getBalance();
            double updatedBalance = currentBalance - rest;
            bankAccountRepository.findByAccountOperationsContaining(account).setBalance(updatedBalance);
            accountOperation.setStatus(status);}
        else accountOperation.setStatus(OperationStatus.ACCEPTED);

        return accountOperation;
    }
    @Override
    public TransferRequests UpdateTransferStatus(Long transferId, String status){
        TransferRequests transferRequests = transferRequestsRepository.getTransferRequestsByTransferId(transferId);
        AccountOperation creditOperation=accountOperationRepository.findByOperationId(transferRequests.getCreditId());
        AccountOperation debitOperation=accountOperationRepository.findByOperationId(transferRequests.getDebitId());
        if(Objects.equals(status, "DENIED")){
            double creditRest = creditOperation.getAmount();
            double debitRest = debitOperation.getAmount();
            double creditCurrentBalance = bankAccountRepository.findByAccountOperationsContaining(creditOperation).getBalance();
            double debitCurrentBalance = bankAccountRepository.findByAccountOperationsContaining(debitOperation).getBalance();
            double updatedCreditBalance = creditCurrentBalance - creditRest;
            double updatedDebitBalance = debitCurrentBalance + debitRest;
            bankAccountRepository.findByAccountOperationsContaining(creditOperation).setBalance(updatedCreditBalance);
            bankAccountRepository.findByAccountOperationsContaining(debitOperation).setBalance(updatedDebitBalance);
            transferRequests.setStatus("DENIED");
            creditOperation.setStatus(OperationStatus.DENIED);
            debitOperation.setStatus(OperationStatus.DENIED);
        }else {creditOperation.setStatus(OperationStatus.ACCEPTED);
                creditOperation.setStatus(OperationStatus.ACCEPTED);
                transferRequests.setStatus("ACCEPTED");
        }
        return transferRequests;
    }
    @Override
    public void deleteCustomer(String customerId){
        customerRepository.deleteById(customerId);
    }
    @Override
    public List<AccountOperationDTO> accountHistory(String accountId){
        List<AccountOperation> accountOperations = accountOperationRepository.findByBankAccountAccId(accountId);
        return accountOperations.stream().map(op->dtoMapper.fromAccountOperation(op)).collect(Collectors.toList());
    }
    @Override
    public AccountHistoryDTO getAccountHistory(String accountId, int page, int size) throws BankAccountNotFoundException {
        BankAccount bankAccount=bankAccountRepository.findById(accountId).orElse(null);
        if(bankAccount==null) throw new BankAccountNotFoundException("Account not Found");
        Page<AccountOperation> accountOperations = accountOperationRepository.findByBankAccountAccIdOrderByOperationDateDesc(accountId, PageRequest.of(page, size));
        AccountHistoryDTO accountHistoryDTO=new AccountHistoryDTO();
        List<AccountOperationDTO> accountOperationDTOS = accountOperations.getContent().stream().map(op -> dtoMapper.fromAccountOperation(op)).collect(Collectors.toList());
        accountHistoryDTO.setAccountOperationDTOS(accountOperationDTOS);
        accountHistoryDTO.setAccountId(bankAccount.getAccId());
        accountHistoryDTO.setBalance(bankAccount.getBalance());
        accountHistoryDTO.setCurrentPage(page);
        accountHistoryDTO.setPageSize(size);
        accountHistoryDTO.setTotalPages(accountOperations.getTotalPages());
        return accountHistoryDTO;
    }
    @Override
    public List<AccountOperation> getAccountOperationsUnderOneCustomer(String email, List<AccountOperation> operationsByStatus){
        Customer customer = customerRepository.findByEmailId(email);
        List<BankAccount> bankAccounts = customer.getBankAccounts();
        List<AccountOperation> customerAccountOperations = new ArrayList<>();
        for(BankAccount b: bankAccounts){
            for(AccountOperation a:operationsByStatus){
                if(a.getBankAccount() == b){
                    customerAccountOperations.add(a);
                }
            }
        }
        return customerAccountOperations;
    }
    @Override
    public List<BankAccount> getBankAccountsBasedByStatus(String customerId, AccountStatus status){
        Customer customer = customerRepository.findByEmailId(customerId);
        if(status == AccountStatus.CREATED){return bankAccountRepository.findBankAccountsByStatusAndCustomer(AccountStatus.CREATED, customer);}
        else if(status == AccountStatus.ACTIVATED){return bankAccountRepository.findBankAccountsByStatusAndCustomer(AccountStatus.ACTIVATED, customer);}
        else {return bankAccountRepository.findBankAccountsByStatusAndCustomer(AccountStatus.SUSPENDED, customer);}
    }
    @Override
    public BankAccount updateAccountToSuspendedOrActivated(String customerId, String accountId, AccountStatus status){
        Customer customer = customerRepository.findByEmailId(customerId);
        List<BankAccount> bankAccounts = bankAccountRepository.findBankAccountsByStatusAndCustomer(AccountStatus.CREATED, customer);
        List<BankAccount> bank = new ArrayList<>();
        for(BankAccount b:bankAccounts){
            if(Objects.equals(b.getAccId(), accountId)){
                b.setStatus(status);
                bank.add(b);

            }
        }
        return bank.get(0);

    }
    @Override
    public AccountHistoryDTO getAccountHistoryOfCustomer(String email, String accountId, int page, int size) throws BankAccountNotFoundException {
        BankAccount bankAccount=bankAccountRepository.findById(accountId).orElse(null);
        if(bankAccount==null) throw new BankAccountNotFoundException("Account not Found");
        if(!Objects.equals(bankAccount.getCustomer().getEmailId(), email))throw new Error("This customer doesn't have this account");
        Page<AccountOperation> accountOperations = accountOperationRepository.findByBankAccountAccIdOrderByOperationDateDesc(accountId, PageRequest.of(page, size));
        AccountHistoryDTO accountHistoryDTO=new AccountHistoryDTO();
        List<AccountOperationDTO> accountOperationDTOS = accountOperations.getContent().stream().map(op -> dtoMapper.fromAccountOperation(op)).collect(Collectors.toList());
        accountHistoryDTO.setAccountOperationDTOS(accountOperationDTOS);
        accountHistoryDTO.setAccountId(bankAccount.getAccId());
        accountHistoryDTO.setBalance(bankAccount.getBalance());
        accountHistoryDTO.setCurrentPage(page);
        accountHistoryDTO.setPageSize(size);
        accountHistoryDTO.setTotalPages(accountOperations.getTotalPages());
        return accountHistoryDTO;
    }

    @Override
    public List<CustomerDTO> searchCustomers(String keyword) {
        List<Customer> customers=customerRepository.searchCustomer(keyword);
        List<CustomerDTO> customerDTOS = customers.stream().map(cust -> dtoMapper.fromCustomer(cust)).collect(Collectors.toList());
        return customerDTOS;
    }

    @Override
    public List<BankAccount> bankAccountListByCustomer(String customerId){
        Customer customer = customerRepository.findByEmailId(customerId);
        return bankAccountRepository.findByCustomer(customer);
    }
    @Override
    public List<BankAccount> bankAccountListByCustomerEmail(String customerId){
        Customer customer = customerRepository.findByEmailId(customerId);
        return bankAccountRepository.findByCustomer(customer);
    }


}
