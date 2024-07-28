package com.hat.hereandthere.tourservice.domains.place;

import com.hat.hereandthere.tourservice.domains.place.model.dto.GetPlaceDetailDto;
import com.hat.hereandthere.tourservice.domains.place.model.dto.GetPlaceMetaDto;
import com.hat.hereandthere.tourservice.domains.place.model.dto.GetPlacesPageDto;
import com.hat.hereandthere.tourservice.domains.place.model.response.GetPlaceDetailRes;
import com.hat.hereandthere.tourservice.domains.place.model.response.GetPlacesRes;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
        // TODO: 채팅개수 많은 순서대로 가져오기
        GetPlacesPageDto dto = null;
        if (majorRegionid != null && areaId == null && sigunguId == null) {
            dto = placeService.getPlacesPageDtoByMajorRegionId(majorRegionid, pageable);
        }

        if (majorRegionid == null && areaId != null && sigunguId != null) {
            dto = placeService.getPlacesPageDtoBySigungu(areaId, sigunguId, pageable);
        }

        if (dto == null) {
            dto = placeService.getPlacesPage(pageable);
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
