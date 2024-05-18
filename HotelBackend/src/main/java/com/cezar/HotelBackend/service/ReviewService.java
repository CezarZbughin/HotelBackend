package com.cezar.HotelBackend.service;

import com.cezar.HotelBackend.model.EndUser;
import com.cezar.HotelBackend.model.Hotel;
import com.cezar.HotelBackend.model.Review;
import com.cezar.HotelBackend.repository.ReviewRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {
    @Autowired
    ReviewRepository reviewRepository;
    @Autowired
    HotelService hotelService;

    public List<Review> getAll(){
        return reviewRepository.findAll();
    }

    public List<Review> getByHotel(Hotel hotel){
        return reviewRepository.findAll()
                .stream()
                .filter(review -> review.getId().equals(hotel.getId()))
                .toList();
    }

    public List<Review> getByHotelId(long hotelId) {
        Hotel hotel = hotelService.getById(hotelId).orElseThrow(EntityNotFoundException::new);
        return getByHotel(hotel);
    }

    public Review createReview(EndUser user, Hotel hotel, int rating, String comment){
        Review review = new Review();
        review.setUser(user);
        review.setHotel(hotel);
        review.setRating(rating);
        review.setComment(comment);
        return reviewRepository.save(review);
    }
}
