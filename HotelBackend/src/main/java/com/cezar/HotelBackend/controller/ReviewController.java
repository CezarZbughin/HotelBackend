package com.cezar.HotelBackend.controller;

import com.cezar.HotelBackend.dto.CreateReviewRequest;
import com.cezar.HotelBackend.model.EndUser;
import com.cezar.HotelBackend.model.Hotel;
import com.cezar.HotelBackend.model.Review;
import com.cezar.HotelBackend.service.EndUserService;
import com.cezar.HotelBackend.service.HotelService;
import com.cezar.HotelBackend.service.ReviewService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping("api/v1/review")
public class ReviewController {
    @Autowired
    ReviewService reviewService;
    @Autowired
    EndUserService endUserService;
    @Autowired
    private HotelService hotelService;

    @GetMapping("/all")
    public ResponseEntity<?> getAll(){
        return ResponseEntity.ok(reviewService.getAll());
    }

    @GetMapping("/hotel/{id}")
    public ResponseEntity<?> getAll(@PathVariable long id){
        return ResponseEntity.ok(reviewService.getByHotelId(id));
    }

    @PostMapping("")
    public ResponseEntity<?> createReview(Principal principal, @RequestBody CreateReviewRequest createReviewRequest){
        String username = principal.getName();
        EndUser user = endUserService.getByUsername(username).orElseThrow(EntityNotFoundException::new);
        Hotel hotel = hotelService.getById(createReviewRequest.getHotelId())
                .orElseThrow(EntityNotFoundException::new);

        Review review = reviewService.createReview(
                user,
                hotel,
                createReviewRequest.getRating(),
                createReviewRequest.getComment()
        );

        return ResponseEntity.ok(review);
    }


}
