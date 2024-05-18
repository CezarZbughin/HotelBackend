package com.cezar.HotelBackend.service.exception;

public class ReservationException extends Exception {
    public ReservationException(){
        super("Reservation Exception");
    }

    public ReservationException(String message) {
        super(message);
    }
}
