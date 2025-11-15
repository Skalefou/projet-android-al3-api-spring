package com.groupe.projet_android_AL.dtos.listings;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class ListingRequestDTO {
    @NotBlank(message = "Title is mandatory")
    @Size(min = 1, max = 200, message = "Title must be between 1 and 200 characters")
    public String title;

    @NotBlank(message = "Description is mandatory")
    @Size(min = 10, max = 2000, message = "Description must be between 10 and 2000 characters")
    public String description;

    @NotNull(message = "Price per night is mandatory")
    @Positive(message = "Price per night must be positive")
    public Integer priceByNight;
}

