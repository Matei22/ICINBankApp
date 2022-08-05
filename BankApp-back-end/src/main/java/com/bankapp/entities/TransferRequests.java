package com.bankapp.entities;

import com.bankapp.enums.OperationStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.security.PrivateKey;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransferRequests {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transferId;
    private String emailIdFrom;
    private String emailIdTo;
    double amount;
    private Long creditId;
    private Long debitId;
    private String status;
}
