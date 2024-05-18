package com.cezar.HotelBackend.controller;

import com.cezar.HotelBackend.dto.LoginRequest;
import com.cezar.HotelBackend.service.HotelService;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/v1/hotel")
@CrossOrigin("*")
public class HotelController {
    @Autowired
    HotelService hotelService;

    @GetMapping("")
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(hotelService.getAll());
    }

    @GetMapping("in-range/{lat}/{lng}/{radius}")
    public ResponseEntity<?> getInRange(@PathVariable double lat, @PathVariable double lng, @PathVariable double radius) {
        return ResponseEntity.ok(hotelService.getInRange(lat, lng, radius));
    }
}
