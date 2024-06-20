package com.hat.hereandthere.tourservice.domains.place.model.dto;

import lombok.Builder;
import lombok.Data;

import java.sql.Time;

@Data
@Builder
public class GetPlaceDetailDto {
    private String id;
    private String name;
    private String imageUrl;
    private String contact;
    private Time openingHours;
    private Time closingHours;
    private Area area;
    private Sigungu sigungu;
    private String address;

    @Data
    @Builder
    public static class Area {
        private String id;
        private String name;
    }

    @Data
    @Builder
    public static class Sigungu {
        private String id;
        private String name;
    }
}
