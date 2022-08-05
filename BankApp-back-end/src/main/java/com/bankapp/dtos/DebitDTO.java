package com.bankapp.dtos;

import com.bankapp.enums.OperationStatus;
import lombok.Data;

@Data
public class DebitDTO {
    private String accountId;
    private double amount;
    private String description;
    private OperationStatus status;
}
