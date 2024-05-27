package com.hat.hereandthere.tourservice.domains.place.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetPlaceswithPageDto {
    private String addr1;
    private String areacode;
    private String contentid;
    private String contenttypeid;
    private String firstimage;
    private String firstimage2;
    private String modifiedtime;
    private String sigungucode;
    private String tel;
    private String title;
}
