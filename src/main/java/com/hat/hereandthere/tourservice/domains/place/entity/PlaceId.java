package com.hat.hereandthere.tourservice.domains.place.entity;

import com.hat.hereandthere.tourservice.domains.sigungu.entity.SigunguId;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@EqualsAndHashCode
@NoArgsConstructor
@Getter
public class PlaceId implements java.io.Serializable{
    private String id;
    private SigunguId sigungu;
}
