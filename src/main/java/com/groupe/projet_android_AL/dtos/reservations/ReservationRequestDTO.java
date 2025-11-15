package com.groupe.projet_android_AL.dtos.reservations;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;

public class ReservationRequestDTO {
    @NotNull(message = "Listing ID is mandatory")
    @Positive(message = "Listing ID must be positive")
    public Integer listingId;

    @NotNull(message = "Check-in date is mandatory")
    @Future(message = "Check-in date must be in the future")
    public LocalDate checkInDate;

    @NotNull(message = "Check-out date is mandatory")
    @Future(message = "Check-out date must be in the future")
    public LocalDate checkOutDate;
}

