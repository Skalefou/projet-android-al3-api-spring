package com.groupe.projet_android_AL.dtos;

import com.groupe.projet_android_AL.models.Listings;

public class ListingResponseDTO {
    public Integer id;
    public String title;
    public String description;
    public Integer priceByNight;
    public Integer ownerId;
    public String ownerName;

    public ListingResponseDTO(Listings listing) {
        this.id = listing.getId();
        this.title = listing.getTitle();
        this.description = listing.getDescription();
        this.priceByNight = listing.getPriceByNight();
        
        if (listing.getOwner() != null) {
            this.ownerId = listing.getOwner().getId();
            this.ownerName = listing.getOwner().getFirstName() + " " + listing.getOwner().getLastName();
        }
    }
}

