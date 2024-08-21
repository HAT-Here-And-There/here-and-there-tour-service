package com.hat.hereandthere.tourservice.domains.place.model.dto;

import java.util.List;

public record ChatCountDto(
        List<Item> placeChatCountList
) {
    public record Item(
            Long placeId,
            Long chatCount
    ) {
    }
}

