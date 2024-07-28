package com.hat.hereandthere.tourservice.domains.place.model.dto;

import lombok.Builder;

@Builder
public record GetPlaceMetaDto(String placeId, Long majorRegionId, String sigunguId) {

}
