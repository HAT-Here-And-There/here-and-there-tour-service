package com.hat.hereandthere.tourservice.domains.place;

import static com.hat.hereandthere.tourservice.common.exception.BaseExceptionStatus.PLACE_NOT_FOUND;

import com.hat.hereandthere.tourservice.common.exception.BaseException;
import com.hat.hereandthere.tourservice.domains.place.entity.Place;
import com.hat.hereandthere.tourservice.domains.place.model.dto.GetPlaceDetailDto;
import com.hat.hereandthere.tourservice.domains.place.model.dto.GetPlacesPageDto;
import java.util.List;
import java.util.Optional;
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

        if (places.isEmpty()) {
            throw new BaseException(PLACE_NOT_FOUND);
        }

        return GetPlacesPageDto.builder()
            .places(places.stream().map(place -> GetPlacesPageDto.Place.builder()
                .id(place.getId())
                .imageUrl(place.getImageUrl())
                .build()).toList())
            .totalPages(placePage.getTotalPages())
            .build();
    }

    public GetPlaceDetailDto getPlaceDetail(Long placeId) {
        Optional<Place> place = placeRepository.findById(placeId);

        if (place.isEmpty()) {
            throw new BaseException(PLACE_NOT_FOUND);
        }

        return GetPlaceDetailDto.builder()
            .id(place.get().getId())
            .name(place.get().getName())
            .imageUrl(place.get().getImageUrl())
            .contact(place.get().getContact())
            .openingHours(place.get().getOpeningHours())
            .closingHours(place.get().getClosingHours())
            .area(GetPlaceDetailDto.Area.builder()
                .id(place.get().getSigungu().getArea().getId())
                .name(place.get().getSigungu().getArea().getName())
                .build())
            .sigungu(GetPlaceDetailDto.Sigungu.builder()
                .id(place.get().getSigungu().getId())
                .name(place.get().getSigungu().getName())
                .build())
            .build();
    }
}