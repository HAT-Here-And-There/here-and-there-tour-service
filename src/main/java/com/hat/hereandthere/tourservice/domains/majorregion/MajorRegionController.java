package com.hat.hereandthere.tourservice.domains.majorregion;

import com.hat.hereandthere.tourservice.domains.majorregion.model.GetMajorRegionDto;
import com.hat.hereandthere.tourservice.domains.majorregion.model.MajorRegionResponse;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/major-region")
public class MajorRegionController {

  private final MajorRegionService majorRegionService;

  public MajorRegionController(MajorRegionService majorRegionService) {

    this.majorRegionService = majorRegionService;
  }

  @GetMapping
  private ResponseEntity<List<MajorRegionResponse>> getMajorRegion() {
    final List<GetMajorRegionDto> majorRegionDtoList = majorRegionService.getMajorRegion();

    return ResponseEntity.ok(
        majorRegionDtoList.stream().map(MajorRegionResponse::fromDTO).toList()
    );
  }
}
