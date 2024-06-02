package com.hat.hereandthere.tourservice.domains.majorregion.model;

import com.hat.hereandthere.tourservice.domains.sigungu.model.SigunguResponse;
import java.util.List;

public record MajorRegionResponse(
    long id,
    String name,
    String imageUrl,
    List<SigunguResponse> sigungu
) {

  public static MajorRegionResponse fromDTO(GetMajorRegionDto getMajorRegionDto) {
    return new MajorRegionResponse(
        getMajorRegionDto.id(),
        getMajorRegionDto.name(),
        getMajorRegionDto.imageUrl(),
        getMajorRegionDto.sigunguList().stream()
            .map(sigunguDto -> new SigunguResponse(
                sigunguDto.id(),
                sigunguDto.areaId(),
                sigunguDto.name()))
            .toList()
    );
  }
}
