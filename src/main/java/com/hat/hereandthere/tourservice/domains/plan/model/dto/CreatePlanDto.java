package com.hat.hereandthere.tourservice.domains.plan.model.dto;


import java.time.LocalDate;
import java.util.List;

public record CreatePlanDto(
        String name,
        LocalDate startDate,
        LocalDate endDate,
        List<CreatePlanDtoDailyPlan> dailyPlans
) {
    public record CreatePlanDtoDailyPlan(
            List<CreatePlanDtoDailyPlanItem> dailyPlanItems
    ) {
    }

    public record CreatePlanDtoDailyPlanItem(
            String placeId,
            String memo
    ) {
    }
}
