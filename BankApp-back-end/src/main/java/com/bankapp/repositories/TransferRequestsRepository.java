package com.bankapp.repositories;

import com.bankapp.entities.TransferRequests;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransferRequestsRepository extends JpaRepository<TransferRequests,Long> {
    TransferRequests getTransferRequestsByTransferId(Long transferId);
    List<TransferRequests> getTransferRequestsByEmailIdFromAndStatus(String email, String status);
}
