package com.bankapp.dtos;

import com.bankapp.enums.OperationStatus;
import lombok.Data;

@Data
public class TransferRequestDTO {
    private String accountSource;
    private String accountDestination;
    private double amount;
    private String description;
    private OperationStatus status;
}
