package com.groupe.projet_android_AL.controllers;

import com.groupe.projet_android_AL.auth.annotations.CurrentUser;
import com.groupe.projet_android_AL.dtos.ReservationRequestDTO;
import com.groupe.projet_android_AL.dtos.ReservationResponseDTO;
import com.groupe.projet_android_AL.models.Users;
import com.groupe.projet_android_AL.services.ReservationsService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Controller
@RequestMapping("/api/reservations")
@CrossOrigin(origins = "*")
public class ReservationsController {
    private final ReservationsService reservationsService;

    public ReservationsController(ReservationsService reservationsService) {
        this.reservationsService = reservationsService;
    }

    @PostMapping
    public ResponseEntity<ReservationResponseDTO> createReservation(
            @Valid @RequestBody ReservationRequestDTO requestDTO,
            @CurrentUser Users user) {
        ReservationResponseDTO reservation = reservationsService.createReservation(requestDTO, user);
        return ResponseEntity.status(HttpStatus.CREATED).body(reservation);
    }

    @GetMapping
    public ResponseEntity<List<ReservationResponseDTO>> getUserReservations(@CurrentUser Users user) {
        List<ReservationResponseDTO> reservations = reservationsService.getUserReservations(user);
        return ResponseEntity.ok(reservations);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservationResponseDTO> getReservationById(
            @PathVariable Integer id,
            @CurrentUser Users user) {
        ReservationResponseDTO reservation = reservationsService.getReservationById(id, user);
        return ResponseEntity.ok(reservation);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancelReservation(
            @PathVariable Integer id,
            @CurrentUser Users user) {
        reservationsService.cancelReservation(id, user);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/listing/{listingId}")
    public ResponseEntity<List<ReservationResponseDTO>> getListingReservations(
            @PathVariable Integer listingId,
            @CurrentUser Users user) {
        List<ReservationResponseDTO> reservations = reservationsService.getListingReservations(listingId, user);
        return ResponseEntity.ok(reservations);
    }
}

