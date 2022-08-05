package com.bankapp.entities;

import com.bankapp.enums.OperationStatus;
import com.bankapp.enums.OperationType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.persistence.*;
import java.util.Date;
@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class AccountOperation {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long operationId;
    private Date operationDate;
    private double amount;
    @Enumerated(EnumType.STRING)
    private OperationType type;
    @Enumerated(EnumType.STRING)
    private OperationStatus status;
    @JsonIgnore
    @ManyToOne
    private BankAccount bankAccount;
    private String description;
}

