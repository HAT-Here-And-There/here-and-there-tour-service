package com.hat.hereandthere.tourservice.domains.majorregion.model;

import java.util.List;

public record GetMajorRegionDto(
    long id,
    String name,
    String imageUrl,
    List<SigunguDto> sigunguList
) {

  public record SigunguDto(
      String id,
      String areaId,
      String name
  ) {

  }
}
