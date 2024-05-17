package com.cezar.HotelBackend.repository;

import com.cezar.HotelBackend.model.Hotel;
import com.cezar.HotelBackend.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long>{
}
