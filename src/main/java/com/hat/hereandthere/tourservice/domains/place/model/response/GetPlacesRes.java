package com.hat.hereandthere.tourservice.domains.place.model.response;

import com.hat.hereandthere.tourservice.domains.place.model.dto.GetPlacesPageDto;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetPlacesRes {
    private List<Place> places;
    private int totalPages;

    @Builder
    @Data
    public static class Place {
        private String id;
        private String name;
        private String imageUrl;
    }

    public static GetPlacesRes from(GetPlacesPageDto dto) {
        List<Place> places = dto.getPlaces().stream()
            .map(place -> Place.builder()
                .id(place.getId())
                .name(place.getName())
                .imageUrl(place.getImageUrl())
                .build())
            .collect(Collectors.toList());

        return GetPlacesRes.builder()
            .places(places)
            .totalPages(dto.getTotalPages())
            .build();
    }
}
