package com.cezar.HotelBackend.service;

import org.springframework.stereotype.Service;

@Service
public class GeolocationService {
    public static final double EARTH_RADIUS = 6371;
    public double distance(double lat1, double lng1, double lat2, double lng2) {
        double deltaLat = Math.toRadians(lat2-lat1);  // deg2rad below
        double deltaLng = Math.toRadians(lng2-lng1);
        double chordLengthSq =
                Math.sin(deltaLat/2) * Math.sin(deltaLat/2) + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.sin(deltaLng/2) * Math.sin(deltaLng/2);
        double angularDistance = 2 * Math.atan2(Math.sqrt(chordLengthSq), Math.sqrt(1-chordLengthSq));
        return EARTH_RADIUS * angularDistance;
    }
}
