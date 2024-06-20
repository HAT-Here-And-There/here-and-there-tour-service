package com.hat.hereandthere.tourservice.domains.likedplace.entity;

import com.hat.hereandthere.tourservice.domains.place.entity.PlaceId;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@EqualsAndHashCode
public class LikedPlaceId implements Serializable {
    private Long userId;
    private PlaceId place;
}
