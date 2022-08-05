package com.bankapp.repositories;

import com.bankapp.entities.AccountOperation;
import com.bankapp.enums.AccountStatus;
import com.bankapp.enums.OperationStatus;
import com.bankapp.enums.OperationType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AccountOperationRepository extends JpaRepository<AccountOperation,Long> {
  List<AccountOperation> findByBankAccountAccId(String accountId);
  Page<AccountOperation> findByBankAccountAccIdOrderByOperationDateDesc(String accountId, Pageable pageable);
  List<AccountOperation> findByTypeAndStatus(OperationType type, OperationStatus status);
  AccountOperation findByOperationId(Long id);

}
