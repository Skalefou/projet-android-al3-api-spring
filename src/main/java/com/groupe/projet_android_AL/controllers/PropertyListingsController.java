package com.groupe.projet_android_AL.controllers;

import com.groupe.projet_android_AL.auth.annotations.CurrentUser;
import com.groupe.projet_android_AL.dtos.listings.ListingRequestDTO;
import com.groupe.projet_android_AL.dtos.listings.ListingResponseDTO;
import com.groupe.projet_android_AL.dtos.listings.ListingSearchCriteriaDTO;
import com.groupe.projet_android_AL.models.Users;
import com.groupe.projet_android_AL.services.FavoriteService;
import com.groupe.projet_android_AL.services.ListingsService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@Controller
@RequestMapping("/api/listings")
@CrossOrigin(origins = "*")
public class PropertyListingsController {
    private final ListingsService listingsService;
    private final FavoriteService favoriteService;

    public PropertyListingsController(ListingsService listingsService, FavoriteService favoriteService) {
        this.listingsService = listingsService;
        this.favoriteService = favoriteService;
    }

    @PostMapping
    public ResponseEntity<ListingResponseDTO> createListing(
            @Valid @RequestBody ListingRequestDTO requestDTO,
            @CurrentUser Users user) {
        System.out.println(user.getEmail());
        Optional<Users> userOptional = Optional.ofNullable(user);
        ListingResponseDTO listing = listingsService.createListing(requestDTO, userOptional.orElse(new Users()));
        return ResponseEntity.status(HttpStatus.CREATED).body(listing);
    }

    @GetMapping
    public ResponseEntity<List<ListingResponseDTO>> getAllListings() {
        List<ListingResponseDTO> listings = listingsService.getAllListings();
        return ResponseEntity.ok(listings);
    }

    @GetMapping("/my-listings")
    public ResponseEntity<List<ListingResponseDTO>> getMyListings(@CurrentUser Users user) {
        List<ListingResponseDTO> listings = listingsService.getUserListings(user);
        return ResponseEntity.ok(listings);
    }

    @GetMapping("/favorites")
    public ResponseEntity<List<ListingResponseDTO>> getUserFavorites(@CurrentUser Users user) {
        List<ListingResponseDTO> favorites = favoriteService.getUserFavorites(user);
        return ResponseEntity.ok(favorites);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ListingResponseDTO> getListingById(@PathVariable Integer id) {
        ListingResponseDTO listing = listingsService.getListingById(id);
        return ResponseEntity.ok(listing);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ListingResponseDTO> updateListing(
            @PathVariable Integer id,
            @Valid @RequestBody ListingRequestDTO requestDTO,
            @CurrentUser Users user) {
        ListingResponseDTO listing = listingsService.updateListing(id, requestDTO, user);
        return ResponseEntity.ok(listing);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteListing(
            @PathVariable Integer id,
            @CurrentUser Users user) {
        listingsService.deleteListing(id, user);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/favorites")
    public ResponseEntity<ListingResponseDTO> addFavorite(
            @PathVariable Integer id,
            @CurrentUser Users user) {
        ListingResponseDTO listing = favoriteService.addFavorite(id, user);
        return ResponseEntity.status(HttpStatus.CREATED).body(listing);
    }

    @DeleteMapping("/{id}/favorites")
    public ResponseEntity<Void> removeFavorite(
            @PathVariable Integer id,
            @CurrentUser Users user) {
        favoriteService.removeFavorite(id, user);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/favorite")
    public ResponseEntity<Map<String, Boolean>> isFavorite(
            @PathVariable Integer id,
            @CurrentUser Users user) {
        boolean isFavorite = favoriteService.isFavorite(id, user);
        return ResponseEntity.ok(Map.of("isFavorite", isFavorite));
    }

    @GetMapping("/search")
    public ResponseEntity<List<ListingResponseDTO>> getFilteredListings(
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String propertyType,

            @RequestParam(required = false) Integer minPrice,
            @RequestParam(required = false) Integer maxPrice,

            @RequestParam(required = false) Integer minRooms,
            @RequestParam(required = false) Integer minBathrooms,
            @RequestParam(required = false) Integer minBeds,

            @RequestParam(required = false) Integer adults,
            @RequestParam(required = false) Integer children,
            @RequestParam(required = false) Integer babies,
            @RequestParam(required = false) Integer pets,

            @RequestParam(required = false) Long checkIn,
            @RequestParam(required = false) Long checkOut
    ) {

        ListingSearchCriteriaDTO criteria = mapToCriteria(
                city,
                propertyType,
                minPrice,
                maxPrice,
                minRooms,
                minBathrooms,
                minBeds,
                adults,
                children,
                babies,
                pets,
                checkIn,
                checkOut
        );

        return ResponseEntity.ok(listingsService.getFilteredListings(criteria));
    }

    private ListingSearchCriteriaDTO mapToCriteria(
            String city,
            String propertyType,
            Integer minPrice,
            Integer maxPrice,
            Integer minRooms,
            Integer minBathrooms,
            Integer minBeds,
            Integer adults,
            Integer children,
            Integer babies,
            Integer pets,
            Long checkIn,
            Long checkOut
    ) {
        ListingSearchCriteriaDTO dto = new ListingSearchCriteriaDTO();

        dto.city = city;
        dto.propertyType = propertyType;

        dto.minPrice = minPrice;
        dto.maxPrice = maxPrice;

        dto.minRooms = minRooms;
        dto.minBathrooms = minBathrooms;
        dto.minBeds = minBeds;

        dto.adults = adults;
        dto.children = children;
        dto.babies = babies;
        dto.pets = pets;

        dto.checkIn = checkIn;
        dto.checkOut = checkOut;

        return dto;
    }


}


