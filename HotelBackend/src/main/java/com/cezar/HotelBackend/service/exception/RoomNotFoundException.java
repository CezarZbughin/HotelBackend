package com.cezar.HotelBackend.service.exception;

public class RoomNotFoundException extends Exception {
    public RoomNotFoundException(){
        super("Room not found");
    }
}
