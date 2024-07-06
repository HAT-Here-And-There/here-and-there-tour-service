package com.hat.hereandthere.tourservice.domains.place.entity;

import com.hat.hereandthere.tourservice.domains.sigungu.entity.Sigungu;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Time;

@Entity(name = "place")
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"id"})
})
@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@IdClass(PlaceId.class)
public class Place {
    @Id
    private String id;

    @Id
    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "sigungu_id", referencedColumnName = "id"),
            @JoinColumn(name = "area_id", referencedColumnName = "area_id")
    })
    private Sigungu sigungu;

    @Column(columnDefinition = "TEXT")
    private String imageUrl;

    @Column
    private String name;

    @Column
    private String contact;

    @Column
    private Time openingHours;

    @Column
    private Time closingHours;

    @Column(columnDefinition = "TEXT")
    private String address;

    @Builder
    public Place(String id, Sigungu sigungu, String imageUrl, String name, String contact, Time openingHours, Time closingHours, String address) {
        this.id = id;
        this.sigungu = sigungu;
        this.imageUrl = imageUrl;
        this.name = name;
        this.contact = contact;
        this.openingHours = openingHours;
        this.closingHours = closingHours;
        this.address = address;
    }
}
