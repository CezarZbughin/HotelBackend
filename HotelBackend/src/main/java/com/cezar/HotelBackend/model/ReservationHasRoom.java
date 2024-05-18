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
@Setter @Getter
public class ReservationHasRoom extends IdentityModel<Long>{
    @ManyToOne
    @JoinColumn(name = "room_id")
    @JsonManagedReference
    @JsonIdentityReference(alwaysAsId=true)
    private Room room;

    @ManyToOne
    @JoinColumn(name = "reservation_id")
    @JsonManagedReference
    @JsonIdentityReference(alwaysAsId=true)
    private Reservation reservation;
}
