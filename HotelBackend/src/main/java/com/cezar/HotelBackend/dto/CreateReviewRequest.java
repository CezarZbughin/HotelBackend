package com.cezar.HotelBackend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter @Getter
public class CreateReviewRequest {
    long hotelId;
    int rating;
    String comment;
}
