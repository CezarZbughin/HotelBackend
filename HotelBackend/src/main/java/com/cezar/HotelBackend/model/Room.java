package com.cezar.HotelBackend.model;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Table(name = "room",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "room_number")
        })
public class Room extends IdentityModel<Long> {
    @ManyToOne
    @JoinColumn(name = "hotel_id")
    @JsonManagedReference
    @JsonIdentityReference(alwaysAsId=true)
    private Hotel hotel;
    private int roomNumber;
    private int type;
    private int price;
    private boolean isAvailable;
}
