package com.cezar.HotelBackend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@CrossOrigin("*")
public class HelloController {
    @GetMapping("/hello")
    public ResponseEntity<?> hello(){
        return ResponseEntity.ok(new WebMessage("Hello World"));
    }

}
