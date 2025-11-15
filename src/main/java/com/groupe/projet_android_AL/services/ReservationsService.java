package com.groupe.projet_android_AL.services;

import com.groupe.projet_android_AL.dtos.reservations.ReservationRequestDTO;
import com.groupe.projet_android_AL.dtos.reservations.ReservationResponseDTO;
import com.groupe.projet_android_AL.models.Listings;
import com.groupe.projet_android_AL.models.Reservations;
import com.groupe.projet_android_AL.models.Users;
import com.groupe.projet_android_AL.repositories.ListingsRepository;
import com.groupe.projet_android_AL.repositories.ReservationsRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ReservationsService {
    private final ReservationsRepository reservationsRepository;
    private final ListingsRepository listingsRepository;

    public ReservationsService(ReservationsRepository reservationsRepository, ListingsRepository listingsRepository) {
        this.reservationsRepository = reservationsRepository;
        this.listingsRepository = listingsRepository;
    }

    public ReservationResponseDTO createReservation(ReservationRequestDTO requestDTO, Users guest) {
        // Valider les dates
        if (requestDTO.checkInDate.isAfter(requestDTO.checkOutDate) || requestDTO.checkInDate.isEqual(requestDTO.checkOutDate)) {
            throw new IllegalArgumentException("Check-out date must be after check-in date");
        }

        if (requestDTO.checkInDate.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Check-in date cannot be in the past");
        }

        // Récupérer le listing
        Listings listing = listingsRepository.findById(requestDTO.listingId)
                .orElseThrow(() -> new IllegalArgumentException("Listing not found"));

        // Vérifier que l'utilisateur ne réserve pas son propre listing
        if (listing.getOwner().getId().equals(guest.getId())) {
            throw new IllegalArgumentException("You cannot reserve your own listing");
        }

        // Vérifier les conflits de réservation
        List<Reservations.ReservationStatus> activeStatuses = List.of(
                Reservations.ReservationStatus.PENDING,
                Reservations.ReservationStatus.CONFIRMED
        );

        List<Reservations> conflictingReservations = reservationsRepository
                .findConflictingReservations(
                        listing,
                        activeStatuses,
                        requestDTO.checkInDate,
                        requestDTO.checkOutDate
                );

        if (!conflictingReservations.isEmpty()) {
            throw new IllegalArgumentException("The listing is already reserved for these dates");
        }

        // Calculer le prix total
        long numberOfNights = ChronoUnit.DAYS.between(requestDTO.checkInDate, requestDTO.checkOutDate);
        int totalPrice = (int) (numberOfNights * listing.getPriceByNight());

        // Créer la réservation
        Reservations reservation = Reservations.builder()
                .checkInDate(requestDTO.checkInDate)
                .checkOutDate(requestDTO.checkOutDate)
                .totalPrice(totalPrice)
                .status(Reservations.ReservationStatus.CONFIRMED)
                .guest(guest)
                .listing(listing)
                .build();

        reservation = reservationsRepository.save(reservation);
        return new ReservationResponseDTO(reservation);
    }

    public List<ReservationResponseDTO> getUserReservations(Users user) {
        List<Reservations> reservations = reservationsRepository.findByGuest(user);
        return reservations.stream()
                .map(ReservationResponseDTO::new)
                .collect(Collectors.toList());
    }

    public ReservationResponseDTO getReservationById(Integer id, Users user) {
        Reservations reservation = reservationsRepository.findByIdAndGuest(id, user)
                .orElseThrow(() -> new IllegalArgumentException("Reservation not found"));
        return new ReservationResponseDTO(reservation);
    }

    public void cancelReservation(Integer id, Users user) {
        Reservations reservation = reservationsRepository.findByIdAndGuest(id, user)
                .orElseThrow(() -> new IllegalArgumentException("Reservation not found"));

        if (reservation.getStatus() == Reservations.ReservationStatus.CANCELLED) {
            throw new IllegalArgumentException("Reservation is already cancelled");
        }

        if (reservation.getStatus() == Reservations.ReservationStatus.COMPLETED) {
            throw new IllegalArgumentException("Cannot cancel a completed reservation");
        }

        // Vérifier que la date de check-in n'est pas passée
        if (reservation.getCheckInDate().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Cannot cancel a reservation after check-in date");
        }

        reservation.setStatus(Reservations.ReservationStatus.CANCELLED);
        reservationsRepository.save(reservation);
    }

    public List<ReservationResponseDTO> getListingReservations(Integer listingId, Users owner) {
        Listings listing = listingsRepository.findByIdAndOwner(listingId, owner)
                .orElseThrow(() -> new IllegalArgumentException("Listing not found or you are not the owner"));

        List<Reservations> reservations = reservationsRepository.findByListing(listing);
        return reservations.stream()
                .map(ReservationResponseDTO::new)
                .collect(Collectors.toList());
    }
}

