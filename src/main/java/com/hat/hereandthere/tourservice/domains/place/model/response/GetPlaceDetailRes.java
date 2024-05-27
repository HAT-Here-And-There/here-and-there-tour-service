package com.hat.hereandthere.tourservice.domains.place.model.response;

import com.hat.hereandthere.tourservice.domains.place.model.dto.GetPlaceDetailDto;
import java.sql.Time;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetPlaceDetailRes {
    private String id;
    private String name;
    private String imageUrl;
    private String contact;
    private Time openingHours;
    private Time closingHours;
    private String areaName;
    private String sigunguName;
    private String address;

    public static GetPlaceDetailRes from(GetPlaceDetailDto dto) {
        return GetPlaceDetailRes.builder()
            .id(dto.getId())
            .name(dto.getName())
            .imageUrl(dto.getImageUrl())
            .contact(dto.getContact())
            .openingHours(dto.getOpeningHours())
            .closingHours(dto.getClosingHours())
            .areaName(dto.getArea().getName())
            .sigunguName(dto.getSigungu().getName())
            .address(dto.getAddress())
            .build();
    }
}
