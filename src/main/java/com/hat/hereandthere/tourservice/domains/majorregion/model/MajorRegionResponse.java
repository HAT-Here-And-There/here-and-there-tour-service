package com.hat.hereandthere.tourservice.domains.majorregion.model;

import com.hat.hereandthere.tourservice.domains.sigungu.model.SigunguResponse;
import java.util.List;

public record MajorRegionResponse(long id, String name, List<SigunguResponse> sigunguList) {

}
