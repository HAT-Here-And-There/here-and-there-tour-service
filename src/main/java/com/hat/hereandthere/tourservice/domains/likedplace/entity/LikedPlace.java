package com.hat.hereandthere.tourservice.domains.likedplace.entity;

import com.hat.hereandthere.tourservice.domains.place.entity.Place;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;

@Entity(name = "liked_place")
@EntityListeners(AuditingEntityListener.class)
@Getter
@NoArgsConstructor
@IdClass(LikedPlaceId.class)
public class LikedPlace {


    @Id
    private Long userId;

    @Id
    @ManyToOne
    private Place place;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "liked_at", nullable = false, updatable = false)
    @CreatedDate
    private Date likedAt;


    @Builder
    public LikedPlace(Long userId, Place place) {
        this.userId = userId;
        this.place = place;
    }


}
