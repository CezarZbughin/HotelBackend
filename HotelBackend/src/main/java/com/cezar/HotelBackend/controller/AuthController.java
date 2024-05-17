package com.cezar.HotelBackend.controller;

import com.cezar.HotelBackend.configuration.JwtUtil;
import com.cezar.HotelBackend.dto.LoginRequest;
import com.cezar.HotelBackend.dto.LoginResponse;
import com.cezar.HotelBackend.model.EndUser;
import com.cezar.HotelBackend.repository.EndUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    EndUserRepository endUserRepository;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
            );
            String username = authentication.getName();
            EndUser user = endUserRepository.findByUsername(username).orElseThrow(
                    () -> new UsernameNotFoundException(username + " not found" )
            );
            String token = jwtUtil.createToken(user);
            LoginResponse loginResponse = new LoginResponse(user.getId(), username, token);
            return ResponseEntity.ok(loginResponse);
        } catch (BadCredentialsException e){
            WebMessage errorResponse = new WebMessage("Invalid username or password");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        } catch (Exception e){
            WebMessage errorResponse = new WebMessage("Something went wrong");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }
}
