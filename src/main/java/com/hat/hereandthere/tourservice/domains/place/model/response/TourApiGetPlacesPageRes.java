package com.hat.hereandthere.tourservice.domains.place.model.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TourApiGetPlacesPageRes {
    private Response response;

    @Data
    @NoArgsConstructor
    public static class Response {
        private Header header;
        private Body body;
    }

    @Data
    @NoArgsConstructor
    public static class Header {
        private String resultCode;
        private String resultMsg;
    }

    @Data
    @NoArgsConstructor
    public static class Body {
        private Items items;
        private Integer numOfRows;
        private Integer pageNo;
        private Integer totalCount;
    }

    @Data
    @NoArgsConstructor
    public static class Items {
        private List<Item> item = new ArrayList<>();

        @JsonCreator
        public static Items deserializeString(String emptyString) {
            return new Items();
        }
    }

    @Data
    @NoArgsConstructor
    public static class Item {
        private String addr1;
        private String addr2;
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
}
