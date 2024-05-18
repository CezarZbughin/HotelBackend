package com.cezar.HotelBackend.repository;

import com.cezar.HotelBackend.model.ReservationHasRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationHasRoomRepository extends JpaRepository<ReservationHasRoom, Long> {
}
