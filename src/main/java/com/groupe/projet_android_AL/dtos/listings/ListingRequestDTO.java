package com.groupe.projet_android_AL.dtos.listings;

import jakarta.validation.constraints.*;

public class ListingRequestDTO {
    @NotBlank(message = "Title is mandatory")
    @Size(min = 1, max = 200, message = "Title must be between 1 and 200 characters")
    public String title;

    @NotBlank(message = "Description is mandatory")
    @Size(min = 10, max = 2000, message = "Description must be between 10 and 2000 characters")
    public String description;

    @Positive(message = "Number of rooms must be positive")
    public Integer numberOfRooms;

    @Positive(message = "Number of bathrooms must be positive")
    public Integer numberOfBathrooms;

    @Positive(message = "Number of beds must be positive")
    public Integer numberOfBed;

    public Boolean hasWifi;

    public Boolean hasWashingMachine;

    public Boolean hasAirConditioning;

    public Boolean hasTv;

    public Boolean hasParking;

    @Positive(message = "Maximum guests must be positive")
    @Min(value = 1, message = "Maximum guests must be at least 1")
    @Max(value = 10, message = "Maximum guests must be at most 10")
    public Integer maxGuests;

    @NotBlank(message = "Address is mandatory")
    @Size(min = 1, max = 100, message = "Address must be between 1 and 100 characters")
    public String address;

    @NotBlank(message = "Zip code is mandatory")
    @Size(min = 1, max = 6, message = "Zip code must be between 1 and 6 characters")
    public String zipCode;

    @NotBlank(message = "City is mandatory")
    @Size(min = 1, max = 50, message = "City must be between 1 and 50 characters")
    public String city;

    @NotBlank(message = "Country is mandatory")
    @Size(min = 1, max = 50, message = "Country must be between 1 and 50 characters")
    public String country;

    @NotBlank(message = "Listing image is mandatory")
    public String firstImage;

    public String secondImage;

    public String thirdImage;

    @NotNull(message = "Price per night is mandatory")
    @Positive(message = "Price per night must be positive")
    public Integer priceByNight;


}

