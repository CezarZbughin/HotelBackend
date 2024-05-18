package com.cezar.HotelBackend.service;

import com.cezar.HotelBackend.model.Hotel;
import com.cezar.HotelBackend.repository.HotelRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class HotelService {
    @Autowired
    HotelRepository hotelRepository;
    @Autowired
    GeolocationService geolocationService;

    @AllArgsConstructor
    @NoArgsConstructor @Setter @Getter
    public static class HotelAndDistance {
        private Hotel hotel;
        private double distance;
    };
    public List<Hotel> getAll(){
        return hotelRepository.findAll();
    }

    /**
     *
     * @param lat Latitude of the center point
     * @param lng Longitude of the center point
     * @param radius The max distance
     * @return This returns a list of hotels that are within the given radius from the given center point
     */
    public List<HotelAndDistance> getInRange(double lat, double lng, double radius) {
        List<Hotel> hotels = getAll();
        List<HotelAndDistance> result = new ArrayList<>();
        for(Hotel hotel : hotels) {
            double distance =
                    geolocationService.distance(hotel.getLatitude(), hotel.getLongitude(), lat, lng);
            if(distance <= radius) {
                result.add(new HotelAndDistance(hotel, distance));
            }
        }
        result.sort(Comparator.comparingDouble(HotelAndDistance::getDistance));
        return result;
    }
}
