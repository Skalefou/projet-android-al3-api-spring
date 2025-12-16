package com.groupe.projet_android_AL.dtos.listings;

public class ListingSearchCriteriaDTO {
    public String city;
    public Long checkIn;     // epoch millis
    public Long checkOut;    // epoch millis

    public Integer adults;
    public Integer children;
    public Integer babies;
    public Integer pets;

    public String propertyType;   // Chambre / Appartement / Maison

    public Integer minPrice;
    public Integer maxPrice;

    public Integer minRooms;
    public Integer minBathrooms;
    public Integer minBeds;

    public Integer getTotalGuests() {
        int a = adults == null ? 1 : adults;
        int c = children == null ? 0 : children;
        int b = babies == null ? 0 : babies;
        return a + c + b;
    }
}
