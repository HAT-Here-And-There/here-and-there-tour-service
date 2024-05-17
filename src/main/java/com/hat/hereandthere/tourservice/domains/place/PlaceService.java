package com.hat.hereandthere.tourservice.domains.place;

import com.hat.hereandthere.tourservice.domains.place.entity.Place;
import com.hat.hereandthere.tourservice.domains.place.model.dto.GetPlacesPageDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PlaceService {
    private final PlaceRepository placeRepository;

    public GetPlacesPageDto getPlacesPage(Pageable pageable) {
        Page<Place> placePage = placeRepository.findAll(pageable);
        List<Place> places = placePage.getContent();

        return GetPlacesPageDto.builder()
            .places(places.stream().map(place -> GetPlacesPageDto.Place.builder()
                .id(place.getId())
                .imageUrl(place.getImageUrl())
                .build()).toList())
            .totalPages(placePage.getTotalPages())
            .build();
    }
}