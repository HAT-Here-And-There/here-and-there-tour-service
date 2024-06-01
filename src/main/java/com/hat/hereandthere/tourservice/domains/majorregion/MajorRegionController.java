package com.hat.hereandthere.tourservice.domains.majorregion;

import com.hat.hereandthere.tourservice.domains.majorregion.model.MajorRegionResponse;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/major-region")
public class MajorRegionController {

  @GetMapping
  private ResponseEntity<List<MajorRegionResponse>> getMajorRegion() {
    return ResponseEntity.ok(List.of(
        new MajorRegionResponse(1, "서울", List.of()),
        new MajorRegionResponse(2, "경기", List.of()),
        new MajorRegionResponse(3, "인천", List.of()),
        new MajorRegionResponse(4, "강원", List.of()),
        new MajorRegionResponse(5, "충청", List.of()),
        new MajorRegionResponse(6, "전라", List.of()),
        new MajorRegionResponse(7, "경상", List.of()),
        new MajorRegionResponse(8, "제주", List.of())
    ));
  }
}
