package com.groupe.projet_android_AL.repositories;

import com.groupe.projet_android_AL.models.Listings;
import org.springframework.data.jpa.domain.Specification;

public final class ListingSpecifications {

    private ListingSpecifications() {}

    public static Specification<Listings> cityEquals(String city) {
        return (root, query, cb) ->
                city == null || city.isBlank()
                        ? cb.conjunction()
                        : cb.equal(cb.lower(root.get("city")), city.toLowerCase());
    }

    public static Specification<Listings> typeEquals(String type) {
        return (root, query, cb) ->
                type == null || type.isBlank()
                        ? cb.conjunction()
                        : cb.equal(cb.lower(root.get("type")), type.toLowerCase());
    }

    public static Specification<Listings> priceBetween(Integer min, Integer max) {
        return (root, query, cb) -> {
            if (min == null && max == null) return cb.conjunction();
            if (min == null) return cb.lessThanOrEqualTo(root.get("priceByNight"), max);
            if (max == null) return cb.greaterThanOrEqualTo(root.get("priceByNight"), min);
            return cb.between(root.get("priceByNight"), min, max);
        };
    }

    public static Specification<Listings> roomsAtLeast(Integer minRooms) {
        return (root, query, cb) ->
                minRooms == null || minRooms <= 0
                        ? cb.conjunction()
                        : cb.greaterThanOrEqualTo(root.get("numberOfRooms"), minRooms);
    }

    public static Specification<Listings> bathroomsAtLeast(Integer minBathrooms) {
        return (root, query, cb) ->
                minBathrooms == null || minBathrooms <= 0
                        ? cb.conjunction()
                        : cb.greaterThanOrEqualTo(root.get("numberOfBathrooms"), minBathrooms);
    }

    public static Specification<Listings> bedsAtLeast(Integer minBeds) {
        return (root, query, cb) ->
                minBeds == null || minBeds <= 0
                        ? cb.conjunction()
                        : cb.greaterThanOrEqualTo(root.get("numberOfBed"), minBeds);
    }

    public static Specification<Listings> guestsAtLeast(Integer guests) {
        return (root, query, cb) ->
                guests == null || guests <= 0
                        ? cb.conjunction()
                        : cb.greaterThanOrEqualTo(root.get("maxGuests"), guests);
    }
}
