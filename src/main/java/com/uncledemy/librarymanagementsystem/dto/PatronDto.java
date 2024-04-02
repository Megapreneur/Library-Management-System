package com.uncledemy.librarymanagementsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PatronDto {
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private String address;
}
