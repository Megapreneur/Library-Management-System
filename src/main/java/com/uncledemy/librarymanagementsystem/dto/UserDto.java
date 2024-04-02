package com.uncledemy.librarymanagementsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class StaffDto {
    private String name;
    private String username;
    private String password;
    private String confirmPassword;
    private String authority;

}
