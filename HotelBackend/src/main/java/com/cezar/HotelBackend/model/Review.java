package com.cezar.HotelBackend.model;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Review extends IdentityModel<Long> {
    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonManagedReference
    @JsonIdentityReference(alwaysAsId=true)
    private EndUser user;
    @ManyToOne
    @JoinColumn(name = "hotel_id")
    @JsonManagedReference
    @JsonIdentityReference(alwaysAsId=true)
    private Hotel hotel;
    private int rating;
    private String comment;
}
