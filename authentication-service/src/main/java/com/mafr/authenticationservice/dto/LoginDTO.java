package com.mafr.authenticationservice.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@ToString
public class LoginDTO {
    private String phone;
}