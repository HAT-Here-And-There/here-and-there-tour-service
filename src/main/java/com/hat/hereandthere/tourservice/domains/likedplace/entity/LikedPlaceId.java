package com.hat.hereandthere.tourservice.domains.likedplace.entity;

import com.hat.hereandthere.tourservice.domains.place.entity.PlaceId;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@AllArgsConstructor
@EqualsAndHashCode
public class LikedPlaceId implements Serializable {
    private Long userId;
    private PlaceId place;
}
