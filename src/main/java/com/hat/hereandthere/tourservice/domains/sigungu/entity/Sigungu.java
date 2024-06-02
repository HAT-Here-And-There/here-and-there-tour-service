package com.hat.hereandthere.tourservice.domains.sigungu.entity;

import com.hat.hereandthere.tourservice.domains.area.entity.Area;
import com.hat.hereandthere.tourservice.domains.majorregion.entity.MajorRegion;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "sigungu")
@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@IdClass(SigunguId.class)
public class Sigungu {
    @Id
    private String id;

    @ManyToOne
    @JoinColumn(name = "area_id")
    @Id
    private Area area;

    @Column
    private String name;

    @ManyToOne
    @JoinColumn(name = "major_region_id")
    private MajorRegion majorRegion;

    @Builder
    public Sigungu(String id, Area area, String name) {
        this.id = id;
        this.area = area;
        this.name = name;
    }
}
