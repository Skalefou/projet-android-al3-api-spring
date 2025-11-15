package com.groupe.projet_android_AL.repositories;

import com.groupe.projet_android_AL.models.Reservations;
import com.groupe.projet_android_AL.models.Users;
import com.groupe.projet_android_AL.models.Listings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReservationsRepository extends JpaRepository<Reservations, Integer> {
    List<Reservations> findByGuest(Users guest);

    List<Reservations> findByListing(Listings listing);

    List<Reservations> findByListingAndStatus(Listings listing, Reservations.ReservationStatus status);

    @Query("SELECT r FROM Reservations r WHERE r.listing = :listing " +
           "AND r.status IN :statuses " +
           "AND r.checkInDate < :checkOutDate " +
           "AND r.checkOutDate > :checkInDate")
    List<Reservations> findConflictingReservations(
            @Param("listing") Listings listing,
            @Param("statuses") List<Reservations.ReservationStatus> statuses,
            @Param("checkInDate") LocalDate checkInDate,
            @Param("checkOutDate") LocalDate checkOutDate
    );

    Optional<Reservations> findByIdAndGuest(Integer id, Users guest);
}

