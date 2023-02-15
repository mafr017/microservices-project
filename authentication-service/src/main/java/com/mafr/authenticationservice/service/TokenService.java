package com.mafr.authenticationservice.service;

import com.mafr.authenticationservice.dto.LoginDTO;
import com.mafr.authenticationservice.dto.UserDTO;
import com.mafr.authenticationservice.entity.User;
import org.springframework.security.core.Authentication;

public interface TokenService {
    String generatedToken(Authentication authentication);
    LoginDTO decodeToken(String token);
    User addUser(UserDTO userDTO);
}
