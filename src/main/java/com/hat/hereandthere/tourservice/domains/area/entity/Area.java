package com.hat.hereandthere.tourservice.domains.area.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Entity(name = "area")
@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public class Area {
    @Id
    private String id;

    @Column
    private String name;

    @Builder
    public Area(String id, String name) {
        this.id = id;
        this.name = name;
    }
}
