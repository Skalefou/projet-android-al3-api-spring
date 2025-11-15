package com.groupe.projet_android_AL.controllers;

import com.groupe.projet_android_AL.auth.annotations.CurrentUser;
import com.groupe.projet_android_AL.dtos.ListingRequestDTO;
import com.groupe.projet_android_AL.dtos.ListingResponseDTO;
import com.groupe.projet_android_AL.models.Users;
import com.groupe.projet_android_AL.services.ListingsService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Controller
@RequestMapping("/api/listings")
@CrossOrigin(origins = "*")
public class PropertyListingsController {
    private final ListingsService listingsService;

    public PropertyListingsController(ListingsService listingsService) {
        this.listingsService = listingsService;
    }

    @PostMapping
    public ResponseEntity<ListingResponseDTO> createListing(
            @Valid @RequestBody ListingRequestDTO requestDTO,
            @CurrentUser Users user) {
        ListingResponseDTO listing = listingsService.createListing(requestDTO, user);
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
}
