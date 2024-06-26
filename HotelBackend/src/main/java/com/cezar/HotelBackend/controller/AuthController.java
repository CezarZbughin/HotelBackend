package com.cezar.HotelBackend.controller;

import com.cezar.HotelBackend.configuration.JwtUtil;
import com.cezar.HotelBackend.dto.LoginRequest;
import com.cezar.HotelBackend.dto.LoginResponse;
import com.cezar.HotelBackend.dto.RegisterRequest;
import com.cezar.HotelBackend.model.EndUser;
import com.cezar.HotelBackend.repository.EndUserRepository;
import com.cezar.HotelBackend.service.EndUserService;
import com.cezar.HotelBackend.service.exception.UsernameAlreadyExistsException;
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
@CrossOrigin("*")
public class AuthController {

    @Autowired
    EndUserService endUserService;
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
            EndUser user = endUserService.getByUsername(username).orElseThrow(
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

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest) {
        try {
            endUserService.createFromRawPassword(registerRequest.getUsername(), registerRequest.getPassword());
            return ResponseEntity.ok(new WebMessage("Successfully created the user"));
        } catch (UsernameAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new WebMessage("Username already exists"));
        }
    }
}
