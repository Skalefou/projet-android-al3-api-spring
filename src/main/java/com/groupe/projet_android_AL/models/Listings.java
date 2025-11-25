package com.groupe.projet_android_AL.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "listings")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Listings {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "title", length = 50, nullable = false)
    private String title;

    @Column(name = "description", columnDefinition = "TEXT", length = 2000)
    private String description;

    @Column(name = "number_of_rooms", nullable = false)
    private int numberOfRooms;

    @Column(name = "number_of_bathrooms", nullable = false)
    private int numberOfBathrooms;

    @Column(name = "number_of_bed", nullable = false)
    private int numberOfBed;

    @Column(name = "has_wifi", nullable = false)
    private boolean hasWifi;

    @Column(name = "has_washing_machine", nullable = false)
    private boolean hasWashingMachine;

    @Column(name = "has_air_conditioning", nullable = false)
    private boolean hasAirConditioning;

    @Column(name = "has_tv", nullable = false)
    private boolean hasTv;

    @Column(name = "has_parking", nullable = false)
    private boolean hasParking;

    @Column(name = "max_guests", nullable = false)
    private int maxGuests;

    @Column(name = "address", length = 100, nullable = false)
    private String address;

    @Column(name = "zip_code", length = 10, nullable = false)
    private String zipCode;

    @Column(name = "city", length = 50, nullable = false)
    private String city;

    @Column(name = "country", length = 50, nullable = false)
    private String country;

    @Column(name = "first_image", length = 100000, nullable = false)
    private String firstImage;

    @Column(name = "second_image", length = 100000)
    private String secondImage;

    @Column(name = "third_image", length = 100000)
    private String thirdImage;

    @Column(name = "price_by_night", nullable = false)
    private int priceByNight;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private Users owner;
}
