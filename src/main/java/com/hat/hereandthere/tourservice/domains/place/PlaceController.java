package com.hat.hereandthere.tourservice.domains.place;

import com.hat.hereandthere.tourservice.domains.place.model.dto.GetPlaceDetailDto;
import com.hat.hereandthere.tourservice.domains.place.model.dto.GetPlaceMetaDto;
import com.hat.hereandthere.tourservice.domains.place.model.dto.GetPlacesPageDto;
import com.hat.hereandthere.tourservice.domains.place.model.response.GetPlaceDetailRes;
import com.hat.hereandthere.tourservice.domains.place.model.response.GetPlacesRes;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/places")
@RequiredArgsConstructor
public class PlaceController {
    private final PlaceService placeService;

    @GetMapping
    public ResponseEntity<GetPlacesRes> getPlaces(
            @RequestParam(value = "majorRegionId", required = false) Long majorRegionid,
            @RequestParam(value = "areaId", required = false) String areaId,
            @RequestParam(value = "sigunguId", required = false) String sigunguId,
            Pageable pageable
    ) {
        GetPlacesPageDto dto = null;

        // 모든 여행지
        if (majorRegionid == null && areaId == null && sigunguId == null) {
            dto = placeService.getPlaces(pageable);
        }

        // 8권역 필터
        if (majorRegionid != null && areaId == null && sigunguId == null) {
            dto = placeService.getPlacesFilteredByMajorRegion(majorRegionid, pageable);
        }

        // 시군구 필터
        if (majorRegionid == null && areaId != null && sigunguId != null) {
            dto = placeService.getPlacesFilteredBySigungu(areaId, sigunguId, pageable);
        }

        if (dto == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        GetPlacesRes res = GetPlacesRes.from(dto);

        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping("/{placeId}")
    public ResponseEntity<GetPlaceDetailRes> getPlaceDetail(@PathVariable String placeId) {
        GetPlaceDetailDto dto = placeService.getPlaceDetail(placeId);

        GetPlaceDetailRes res = GetPlaceDetailRes.from(dto);

        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping("/meta/{placeId}")
    public ResponseEntity<GetPlaceMetaDto> getPlaceMetadata(@PathVariable String placeId) {
        try {
            GetPlaceMetaDto dto = placeService.getPlaceMetadata(placeId);

            return new ResponseEntity<>(dto, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
}
