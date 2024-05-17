package com.cezar.HotelBackend.repository;


import com.cezar.HotelBackend.model.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HotelRepository extends JpaRepository<Long, Hotel> {
}
