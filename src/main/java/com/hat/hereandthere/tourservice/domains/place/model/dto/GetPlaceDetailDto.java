package com.hat.hereandthere.tourservice.domains.place.model.dto;

import java.sql.Time;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetPlaceDetailDto {
    private Long id;
    private String name;
    private String imageUrl;
    private String contact;
    private Time openingHours;
    private Time closingHours;
    private Area area;
    private Sigungu sigungu;

    @Data
    @Builder
    public static class Area {
        private Long id;
        private String name;
    }

    @Data
    @Builder
    public static class Sigungu {
        private Long id;
        private String name;
    }
}
