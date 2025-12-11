package com.groupe.projet_android_AL.services;

import com.groupe.projet_android_AL.dtos.listings.ListingResponseDTO;
import com.groupe.projet_android_AL.models.Favorite;
import com.groupe.projet_android_AL.models.Listings;
import com.groupe.projet_android_AL.models.Users;
import com.groupe.projet_android_AL.repositories.FavoriteRepository;
import com.groupe.projet_android_AL.repositories.ListingsRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class FavoriteService {
    private final FavoriteRepository favoriteRepository;
    private final ListingsRepository listingsRepository;

    public FavoriteService(FavoriteRepository favoriteRepository, ListingsRepository listingsRepository) {
        this.favoriteRepository = favoriteRepository;
        this.listingsRepository = listingsRepository;
    }

    public ListingResponseDTO addFavorite(Integer listingId, Users user) {
        Listings listing = listingsRepository.findById(listingId)
                .orElseThrow(() -> new IllegalArgumentException("Listing not found"));

        // Vérifier si le favori existe déjà
        if (favoriteRepository.existsByUserAndListing(user, listing)) {
            throw new IllegalArgumentException("Listing is already in favorites");
        }

        Favorite favorite = Favorite.builder()
                .user(user)
                .listing(listing)
                .build();

        favoriteRepository.save(favorite);
        return new ListingResponseDTO(listing);
    }

    public void removeFavorite(Integer listingId, Users user) {
        Listings listing = listingsRepository.findById(listingId)
                .orElseThrow(() -> new IllegalArgumentException("Listing not found"));

        if (!favoriteRepository.existsByUserAndListing(user, listing)) {
            throw new IllegalArgumentException("Listing is not in favorites");
        }

        favoriteRepository.deleteByUserAndListing(user, listing);
    }

    public List<ListingResponseDTO> getUserFavorites(Users user) {
        List<Favorite> favorites = favoriteRepository.findByUser(user);
        return favorites.stream()
                .map(favorite -> new ListingResponseDTO(favorite.getListing()))
                .collect(Collectors.toList());
    }

    public boolean isFavorite(Integer listingId, Users user) {
        Listings listing = listingsRepository.findById(listingId)
                .orElseThrow(() -> new IllegalArgumentException("Listing not found"));
        return favoriteRepository.existsByUserAndListing(user, listing);
    }
}
