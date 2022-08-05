package com.bankapp.dtos;

import lombok.Data;

@Data
public class CustomerDTO {
    private String emailId;
    private String password;
    private String role;
}
