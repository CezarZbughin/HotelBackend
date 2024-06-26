package com.cezar.HotelBackend.service;

import com.cezar.HotelBackend.model.EndUser;
import com.cezar.HotelBackend.model.Reservation;
import com.cezar.HotelBackend.model.ReservationHasRoom;
import com.cezar.HotelBackend.model.Room;
import com.cezar.HotelBackend.repository.ReservationHasRoomRepository;
import com.cezar.HotelBackend.repository.ReservationRepository;
import com.cezar.HotelBackend.repository.RoomRepository;
import com.cezar.HotelBackend.service.exception.ReservationException;
import com.cezar.HotelBackend.service.exception.RoomNotFoundException;
import com.cezar.HotelBackend.service.exception.UserNotFoundException;
import org.hibernate.annotations.NotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReservationService {
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private ReservationHasRoomRepository reservationHasRoomRepository;
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private EndUserService endUserService;

    public List<Reservation> getAll() {
        return reservationRepository.findAll();
    }

    public List<Reservation> getByUser(EndUser user) {
        return reservationRepository.findAll()
                .stream()
                .filter(reservation -> reservation.getUser().getId().equals(user.getId()))
                .toList();
    }

    public Optional<Reservation> getById(long id) {
        return reservationRepository.findById(id);
    }

    public Reservation createReservation(EndUser user, List<Room> rooms,  LocalDate startDate, LocalDate endDate)
            throws ReservationException {
        if(rooms.isEmpty()){
            throw new ReservationException("No rooms provided");
        }
        Reservation reservation = new Reservation();
        reservation.setUser(user);
        reservation.setRooms(new ArrayList<>());
        reservation.setStartDate(startDate);
        reservation.setEndDate(endDate);

        for(Room room : rooms) {
            if(! room.getHotel().getId().equals(rooms.get(0).getHotel().getId())){
                throw new ReservationException("All rooms must be in the same hotel");
            }
            ReservationHasRoom reservationHasRoom = new ReservationHasRoom();
            reservationHasRoom.setReservation(reservation);
            reservationHasRoom.setRoom(room);
            reservation.getRooms().add(reservationHasRoom);
        }

        return reservationRepository.save(reservation);
    }

    public Reservation createReservation(long userId, List<Long> roomIds,  LocalDate startDate, LocalDate endDate)
            throws ReservationException {
        EndUser user = endUserService.getById(userId).orElseThrow(() -> new ReservationException("User not found"));
        List<Room> rooms = new ArrayList<>();
        for(long roomId : roomIds) {
            Room room = roomRepository.findById(roomId).orElseThrow(() -> new ReservationException("Room not found"));
            rooms.add(room);
        }
        return createReservation(user, rooms, startDate, endDate);
    }

    public void cancelReservation(Reservation reservation) throws ReservationException {
        if(LocalDate.now().isAfter(reservation.getStartDate())){
            throw new ReservationException("The Reservation already started");
        }
        if(LocalDate.now().isEqual(reservation.getStartDate()) && LocalDateTime.now().getHour() > 11) {
            throw new ReservationException("Can not cancel the reservation less than two hour before it starts");
        }
        List<ReservationHasRoom> toDelete = reservationHasRoomRepository.findAll()
                .stream()
                .filter(reservationHasRoom -> reservationHasRoom.getReservation().getId().equals(reservation.getId()))
                .toList();
        reservationHasRoomRepository.deleteAll(toDelete);
        reservationRepository.delete(reservation);
    }

    public void cancelReservationAs(EndUser user, Reservation reservation) throws ReservationException {
        if(!reservation.getUser().getId().equals(user.getId())){
            throw new ReservationException("The given user does not own the reservation");
        }
        cancelReservation(reservation);
    }


}
