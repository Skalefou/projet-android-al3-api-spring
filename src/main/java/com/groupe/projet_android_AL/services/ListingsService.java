package com.groupe.projet_android_AL.services;

import com.groupe.projet_android_AL.dtos.listings.ListingRequestDTO;
import com.groupe.projet_android_AL.dtos.listings.ListingResponseDTO;
import com.groupe.projet_android_AL.models.Listings;
import com.groupe.projet_android_AL.models.Users;
import com.groupe.projet_android_AL.repositories.ListingsRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ListingsService {
    private final ListingsRepository listingsRepository;

    public ListingsService(ListingsRepository listingsRepository) {
        this.listingsRepository = listingsRepository;
    }

    public ListingResponseDTO createListing(ListingRequestDTO requestDTO, Users owner) {
        Listings listing = Listings.builder()
                .title(requestDTO.title)
                .description(requestDTO.description)
                .priceByNight(requestDTO.priceByNight)
                .owner(owner)
                .build();

        listing = listingsRepository.save(listing);
        return new ListingResponseDTO(listing);
    }

    public List<ListingResponseDTO> getAllListings() {
        List<Listings> listings = listingsRepository.findAll();
        return listings.stream()
                .map(ListingResponseDTO::new)
                .collect(Collectors.toList());
    }

    public ListingResponseDTO getListingById(Integer id) {
        Listings listing = listingsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Listing not found"));
        return new ListingResponseDTO(listing);
    }

    public List<ListingResponseDTO> getUserListings(Users owner) {
        List<Listings> listings = listingsRepository.findByOwner(owner);
        return listings.stream()
                .map(ListingResponseDTO::new)
                .collect(Collectors.toList());
    }

    public ListingResponseDTO updateListing(Integer id, ListingRequestDTO requestDTO, Users owner) {
        Listings listing = listingsRepository.findByIdAndOwner(id, owner)
                .orElseThrow(() -> new IllegalArgumentException("Listing not found or you are not the owner"));

        listing.setTitle(requestDTO.title);
        listing.setDescription(requestDTO.description);
        listing.setPriceByNight(requestDTO.priceByNight);

        listing = listingsRepository.save(listing);
        return new ListingResponseDTO(listing);
    }

    public void deleteListing(Integer id, Users owner) {
        Listings listing = listingsRepository.findByIdAndOwner(id, owner)
                .orElseThrow(() -> new IllegalArgumentException("Listing not found or you are not the owner"));

        listingsRepository.delete(listing);
    }
}

