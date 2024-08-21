package com.hat.hereandthere.tourservice.domains.place.model.dto;

import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetPlacesPageDto {
    private List<Place> places;
    private int totalPages;

    @Data
    @Builder
    public static class Place {
        private String id;
        private String name;
        private String imageUrl;
        private int chatCount;
    }
}
