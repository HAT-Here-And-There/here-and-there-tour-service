package com.hat.hereandthere.tourservice.domains.plan.model.dto;

import java.util.List;

public record GetPlanDetailDto(
        Long id,
        String name,
        String startDate,
        String endDate,
        List<DailyPlanDto> dailyPlans
) {
    public record DailyPlanDto(
            Long id,
            String date,
            int dayNumber,
            List<DailyPlanItemDto> dailyPlanItems
    ) {
    }

    public record DailyPlanItemDto(
            Long id,
            String memo,
            DailyPlanItemPlaceDto place
    ) {
    }

    public record DailyPlanItemPlaceDto(
            String id,
            String name,
            String imageUrl
    ) {
    }
}
