package com.cezar.HotelBackend.controller;

import com.cezar.HotelBackend.dto.CreateReservationRequest;
import com.cezar.HotelBackend.model.EndUser;
import com.cezar.HotelBackend.model.Reservation;
import com.cezar.HotelBackend.service.EndUserService;
import com.cezar.HotelBackend.service.ReservationService;
import com.cezar.HotelBackend.service.exception.ReservationException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/api/v1/reservation")
public class ReservationController {
    @Autowired
    private ReservationService reservationService;
    @Autowired
    private EndUserService endUserService;

    /**
     * Returns all the reservations in the data base.
     * TODO: Protect this end point in future iterations (when ADMIN role is added). This is for development only.
     */
    @GetMapping("all")
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(reservationService.getAll());
    }

    /**
     *  Extracts the username from the jwt token, and then returns that user's reservations
     */
    @GetMapping("")
    public ResponseEntity<?> getUsersReservations(Principal principal) {
        String username = principal.getName();
        EndUser user = endUserService.getByUsername(username).orElseThrow(()->new UsernameNotFoundException(username));
        List<Reservation> result = reservationService.getByUser(user);
        return ResponseEntity.ok(result);
    }

    @PostMapping("")
    public ResponseEntity<?> createReservation(Principal principal, @RequestBody CreateReservationRequest createReservationRequest) {
        String username = principal.getName();
        EndUser user = endUserService.getByUsername(username).orElseThrow(()->new UsernameNotFoundException(username));
        try {
            Reservation reservation = reservationService.createReservation(
                    user.getId(),
                    createReservationRequest.getRoomIds(),
                    createReservationRequest.getStartDate(),
                    createReservationRequest.getEndDate()
            );
            return ResponseEntity.ok(reservation);
        } catch (ReservationException e) {
            return ResponseEntity.badRequest().body(new WebMessage("Something went wrong"));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> cancelReservation(Principal principal, @PathVariable long id) {
        String username = principal.getName();
        try {
            EndUser user = endUserService.getByUsername(username).orElseThrow(() -> new EntityNotFoundException(username));
            Reservation reservation = reservationService.getById(id).orElseThrow(EntityNotFoundException::new);
            reservationService.cancelReservationAs(user, reservation);
            return ResponseEntity.ok(new WebMessage("Reservation cancel successfully"));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.badRequest().body(new WebMessage("No such reservation"));
        } catch (ReservationException e) {
            return ResponseEntity.badRequest().body(new WebMessage("You don't own this reservation"));
        }
    }

}
