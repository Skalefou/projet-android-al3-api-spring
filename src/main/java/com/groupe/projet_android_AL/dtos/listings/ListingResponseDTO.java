package com.groupe.projet_android_AL.dtos.listings;

import com.groupe.projet_android_AL.models.Listings;

public class ListingResponseDTO {
    public Integer id;
    public String title;
    public String description;
    public int numberOfRooms;
    public int numberOfBathrooms;
    public int numberOfBed;
    public boolean hasWifi;
    public boolean hasWashingMachine;
    public boolean hasAirConditioning;
    public boolean hasTv;
    public boolean hasParking;
    public int maxGuests;
    public String address;
    public String city;
    public String zipCode;
    public String country;
    public String firstImage;
    public String secondImage;
    public String thirdImage;
    public Integer priceByNight;
    public Integer ownerId;
    public String ownerName;

    public ListingResponseDTO(Listings listing) {
        this.id = listing.getId();
        this.title = listing.getTitle();
        this.description = listing.getDescription();
        this.numberOfRooms = listing.getNumberOfRooms();
        this.numberOfBathrooms = listing.getNumberOfBathrooms();
        this.numberOfBed = listing.getNumberOfBed();
        this.hasWifi = listing.isHasWifi();
        this.hasWashingMachine = listing.isHasWashingMachine();
        this.hasAirConditioning = listing.isHasAirConditioning();
        this.hasTv = listing.isHasTv();
        this.hasParking = listing.isHasParking();
        this.maxGuests = listing.getMaxGuests();
        this.address = listing.getAddress();
        this.zipCode = listing.getZipCode();
        this.city = listing.getCity();
        this.country = listing.getCountry();
        this.firstImage = listing.getFirstImage();
        this.secondImage = listing.getSecondImage();
        this.thirdImage = listing.getThirdImage();
        this.priceByNight = listing.getPriceByNight();

        if (listing.getOwner() != null) {
            this.ownerId = listing.getOwner().getId();
            this.ownerName = listing.getOwner().getFirstName() + " " + listing.getOwner().getLastName();
        }
    }
}

