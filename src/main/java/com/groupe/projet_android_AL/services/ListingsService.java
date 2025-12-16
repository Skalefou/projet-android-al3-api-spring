package com.groupe.projet_android_AL.services;

import com.groupe.projet_android_AL.dtos.listings.ListingRequestDTO;
import com.groupe.projet_android_AL.dtos.listings.ListingResponseDTO;
import com.groupe.projet_android_AL.dtos.listings.ListingSearchCriteriaDTO;
import com.groupe.projet_android_AL.models.Listings;
import com.groupe.projet_android_AL.models.Users;
import com.groupe.projet_android_AL.repositories.ListingSpecifications;
import com.groupe.projet_android_AL.repositories.ListingsRepository;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ListingsService {
    private final ListingsRepository listingsRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public ListingsService(ListingsRepository listingsRepository) {
        this.listingsRepository = listingsRepository;
    }

    public ListingResponseDTO createListing(ListingRequestDTO requestDTO, Users owner) {
        Listings listing = Listings.builder()
                .title(requestDTO.title)
                .description(requestDTO.description)
                .type(requestDTO.type)
                .priceByNight(requestDTO.priceByNight)
                .numberOfRooms(requestDTO.numberOfRooms)
                .numberOfBathrooms(requestDTO.numberOfBathrooms)
                .numberOfBed(requestDTO.numberOfBed)
                .hasWifi(requestDTO.hasWifi)
                .hasWashingMachine(requestDTO.hasWashingMachine)
                .hasAirConditioning(requestDTO.hasAirConditioning)
                .hasTv(requestDTO.hasTv)
                .hasParking(requestDTO.hasParking)
                .maxGuests(requestDTO.maxGuests)
                .address(requestDTO.address)
                .zipCode(requestDTO.zipCode)
                .city(requestDTO.city)
                .country(requestDTO.country)
                .firstImage(requestDTO.firstImage)
                .secondImage(requestDTO.secondImage)
                .thirdImage(requestDTO.thirdImage)
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

        entityManager.flush();

        listing = listingsRepository.save(listing);
        return new ListingResponseDTO(listing);
    }

    public void deleteListing(Integer id, Users owner) {
        Listings listing = listingsRepository.findByIdAndOwner(id, owner)
                .orElseThrow(() -> new IllegalArgumentException("Listing not found or you are not the owner"));

        listingsRepository.delete(listing);
    }

    public List<ListingResponseDTO> getFilteredListings(ListingSearchCriteriaDTO c) {
        Integer minPrice = c.minPrice == null ? 1 : c.minPrice;
        Integer maxPrice = c.maxPrice == null ? 15000 : c.maxPrice;

        Specification<Listings> spec = Specification
                .where(ListingSpecifications.cityEquals(c.city))
                .and(ListingSpecifications.typeEquals(c.propertyType))
                .and(ListingSpecifications.priceBetween(minPrice, maxPrice))
                .and(ListingSpecifications.roomsAtLeast(c.minRooms))
                .and(ListingSpecifications.bathroomsAtLeast(c.minBathrooms))
                .and(ListingSpecifications.bedsAtLeast(c.minBeds))
                .and(ListingSpecifications.guestsAtLeast(c.getTotalGuests()));


        return listingsRepository.findAll(spec)
                .stream()
                .map(ListingResponseDTO::new)
                .collect(Collectors.toList());
    }
}

