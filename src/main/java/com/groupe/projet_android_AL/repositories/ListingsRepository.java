package com.groupe.projet_android_AL.repositories;

import com.groupe.projet_android_AL.models.Listings;
import com.groupe.projet_android_AL.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ListingsRepository extends JpaRepository<Listings, Integer> {
    List<Listings> findByOwner(Users owner);
    
    Optional<Listings> findByIdAndOwner(Integer id, Users owner);
}

