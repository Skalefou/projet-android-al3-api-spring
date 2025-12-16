package com.groupe.projet_android_AL.repositories;

import com.groupe.projet_android_AL.models.Listings;
import org.springframework.data.jpa.domain.Specification;

public final class ListingSpecifications {

    private ListingSpecifications() {}

    public static Specification<Listings> cityEquals(String city) {
        return (root, query, cb) -> {
            if (city == null || city.isBlank()) return null;
            return cb.equal(cb.lower(root.get("city")), city.trim().toLowerCase());
        };
    }

    public static Specification<Listings> typeEquals(String type) {
        return (root, query, cb) -> {
            if (type == null || type.isBlank()) return null;
            return cb.equal(cb.lower(root.get("type")), type.trim().toLowerCase());
        };
    }

    public static Specification<Listings> priceBetween(Integer min, Integer max) {
        return (root, query, cb) -> {
            if (min == null && max == null) return null;
            if (min == null) return cb.lessThanOrEqualTo(root.get("priceByNight"), max);
            if (max == null) return cb.greaterThanOrEqualTo(root.get("priceByNight"), min);
            return cb.between(root.get("priceByNight"), min, max);
        };
    }

    public static Specification<Listings> roomsAtLeast(Integer minRooms) {
        return (root, query, cb) -> {
            if (minRooms == null || minRooms <= 0) return null;
            return cb.greaterThanOrEqualTo(root.get("numberOfRooms"), minRooms);
        };
    }

    public static Specification<Listings> bathroomsAtLeast(Integer minBathrooms) {
        return (root, query, cb) -> {
            if (minBathrooms == null || minBathrooms <= 0) return null;
            return cb.greaterThanOrEqualTo(root.get("numberOfBathrooms"), minBathrooms);
        };
    }

    public static Specification<Listings> bedsAtLeast(Integer minBeds) {
        return (root, query, cb) -> {
            if (minBeds == null || minBeds <= 0) return null;
            return cb.greaterThanOrEqualTo(root.get("numberOfBed"), minBeds);
        };
    }

    public static Specification<Listings> guestsAtLeast(Integer guests) {
        return (root, query, cb) -> {
            if (guests == null || guests <= 0) return null;
            return cb.greaterThanOrEqualTo(root.get("maxGuests"), guests);
        };
    }
}

