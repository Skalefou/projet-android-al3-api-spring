package com.groupe.projet_android_AL.dtos.reservations;

import com.groupe.projet_android_AL.models.Reservations;

import java.time.LocalDate;

public class ReservationResponseDTO {
    public Integer id;
    public LocalDate checkInDate;
    public LocalDate checkOutDate;
    public Integer totalPrice;
    public String status;
    public Integer listingId;
    public String listingTitle;
    public Integer guestId;
    public String guestName;

    public ReservationResponseDTO(Reservations reservation) {
        this.id = reservation.getId();
        this.checkInDate = reservation.getCheckInDate();
        this.checkOutDate = reservation.getCheckOutDate();
        this.totalPrice = reservation.getTotalPrice();
        this.status = reservation.getStatus().name();

        if (reservation.getListing() != null) {
            this.listingId = reservation.getListing().getId();
            this.listingTitle = reservation.getListing().getTitle();
        }

        if (reservation.getGuest() != null) {
            this.guestId = reservation.getGuest().getId();
            this.guestName = reservation.getGuest().getFirstName() + " " + reservation.getGuest().getLastName();
        }
    }
}

