package com.groupe.projet_android_AL.repositories;

import com.groupe.projet_android_AL.models.Favorite;
import com.groupe.projet_android_AL.models.Listings;
import com.groupe.projet_android_AL.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, Integer> {
    Optional<Favorite> findByUserAndListing(Users user, Listings listing);
    
    List<Favorite> findByUser(Users user);
    
    boolean existsByUserAndListing(Users user, Listings listing);
    
    void deleteByUserAndListing(Users user, Listings listing);
}
