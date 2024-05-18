package com.cezar.HotelBackend.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
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
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
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
