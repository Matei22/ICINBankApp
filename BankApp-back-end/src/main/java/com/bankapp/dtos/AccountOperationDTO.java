package com.bankapp.dtos;

import com.bankapp.enums.OperationStatus;
import com.bankapp.enums.OperationType;
import lombok.Data;
import java.util.Date;

@Data
public class AccountOperationDTO {
    private Long operationId;
    private Date operationDate;
    private double amount;
    private OperationType type;
    private String description;
    private OperationStatus status;
}

