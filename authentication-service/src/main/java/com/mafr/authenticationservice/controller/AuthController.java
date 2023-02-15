package com.mafr.authenticationservice.controller;

import com.mafr.authenticationservice.dto.LoginDTO;
import com.mafr.authenticationservice.dto.ResponseDTO;
import com.mafr.authenticationservice.dto.UserDTO;
import com.mafr.authenticationservice.entity.User;
import com.mafr.authenticationservice.service.TokenService;
import com.mafr.authenticationservice.service.impl.JpaUserDetailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {
    private final TokenService tokenService;
    private final AuthenticationManager authenticationManager;
    private final JpaUserDetailService jpaUserDetailService;
    @PostMapping("/register")
    public ResponseEntity<ResponseDTO> addUser(@RequestBody UserDTO userDTO) {
        User result = tokenService.addUser(userDTO);
        return new ResponseEntity<>(ResponseDTO.builder()
                .httpStatus(HttpStatus.CREATED)
                .message("success add user")
                .data(result).build(), HttpStatus.CREATED);
    }
    @PostMapping("/login")
    public ResponseEntity<ResponseDTO<String>> token(@RequestBody User user) {

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getPhone(), user.getPassword()));
        String token = tokenService.generatedToken(authentication);
        return new ResponseEntity(ResponseDTO.builder().httpStatus(HttpStatus.OK).message("token created").data(token).build(), HttpStatus.OK);

    }
    @GetMapping("/info")
    public ResponseEntity<ResponseDTO<Object>> userInfo(@RequestHeader(name = "Authorization") String tokenBearer) {
        LoginDTO user = tokenService.decodeToken(tokenBearer);
        return new ResponseEntity(ResponseDTO.builder().httpStatus(HttpStatus.OK).message("token found").data(user).build(), HttpStatus.OK);
    }
}
