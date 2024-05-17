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
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Reservation extends IdentityModel<Long>{
    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonManagedReference
    @JsonIdentityReference(alwaysAsId=true)
    private EndUser user;
    @ManyToOne
    @JoinColumn(name = "room_id")
    @JsonManagedReference
    @JsonIdentityReference(alwaysAsId=true)
    private Room room;
    private LocalDate startDate;
    private LocalDate endDate;
}
