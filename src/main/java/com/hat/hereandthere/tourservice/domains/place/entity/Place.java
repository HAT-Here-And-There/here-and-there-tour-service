package com.hat.hereandthere.tourservice.domains.place.entity;

import com.hat.hereandthere.tourservice.domains.sigungu.entity.Sigungu;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.ManyToOne;
import java.sql.Time;
import java.util.Date;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Entity(name = "place")
@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@IdClass(PlaceId.class)
public class Place {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumns({
        @JoinColumn(name = "sigungu_id", referencedColumnName = "id"),
        @JoinColumn(name = "area_id", referencedColumnName = "area_id")
    })
    @Id
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
}
