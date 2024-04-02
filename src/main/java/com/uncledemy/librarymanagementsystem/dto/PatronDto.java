package com.uncledemy.salesmanagementsystem.dto;

import com.uncledemy.salesmanagementsystem.model.Authority;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ClientDto {
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private String address;
    private String authority;
}
