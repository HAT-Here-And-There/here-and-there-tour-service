package com.hat.hereandthere.tourservice.domains.place;

import com.hat.hereandthere.tourservice.domains.place.model.dto.GetPlaceDetailDto;
import com.hat.hereandthere.tourservice.domains.place.model.response.GetPlaceDetailRes;
import com.hat.hereandthere.tourservice.domains.place.model.dto.GetPlacesPageDto;
import com.hat.hereandthere.tourservice.domains.place.model.response.GetPlacesRes;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/places")
@RequiredArgsConstructor
public class PlaceController {
    private final PlaceService placeService;

    @GetMapping
    public ResponseEntity<GetPlacesRes> getPlaces(Pageable pageable) {
        // TODO: 채팅개수 많은 순서대로 가져오기
        GetPlacesPageDto dto = placeService.getPlacesPage(pageable);

        GetPlacesRes res = GetPlacesRes.from(dto);

        return new ResponseEntity(res, HttpStatus.OK);
    }

    @GetMapping("/{placeId}")
    public ResponseEntity<GetPlaceDetailRes> getPlaceDetail(@PathVariable Long placeId) {
        GetPlaceDetailDto dto = placeService.getPlaceDetail(placeId);

        GetPlaceDetailRes res = GetPlaceDetailRes.from(dto);

        return new ResponseEntity(res, HttpStatus.OK);
    }
}
