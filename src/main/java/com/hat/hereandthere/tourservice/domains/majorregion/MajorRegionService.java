package com.hat.hereandthere.tourservice.domains.majorregion;

import static java.lang.Math.min;

import com.hat.hereandthere.tourservice.domains.majorregion.entity.MajorRegion;
import com.hat.hereandthere.tourservice.domains.majorregion.model.GetMajorRegionDto;
import com.hat.hereandthere.tourservice.domains.majorregion.model.GetMajorRegionDto.SigunguDto;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class MajorRegionService {

  private final MajorRegionRepository repository;

  public MajorRegionService(MajorRegionRepository repository) {
    this.repository = repository;
  }

  public List<GetMajorRegionDto> getMajorRegion() {
    final List<MajorRegion> majorRegions = repository.findAllFetchJoin();

    return majorRegions.stream()
        .map(majorRegion -> {
          final int sigunguCount = min(majorRegion.getSigungus().size(), 8);

          return new GetMajorRegionDto(
              majorRegion.getId(),
              majorRegion.getName(),
              majorRegion.getImageUrl(),
              majorRegion.getSigungus().subList(0, sigunguCount).stream()
                  .map(e -> new SigunguDto(
                      e.getId(),
                      e.getArea().getId(),
                      e.getName()
                  )).toList()
          );
        }).toList();
  }
}
