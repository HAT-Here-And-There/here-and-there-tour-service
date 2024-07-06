package com.hat.hereandthere.tourservice.domains.plan.model.request;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.util.List;

public record PostPlanRequest(
        String name,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        LocalDate startDate,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        LocalDate endDate,
        List<DailyPlanRequest> dailyPlans
) {

    public record DailyPlanRequest(
            List<DailyPlanItemRequest> dailyPlanItems
    ) {
    }

    public record DailyPlanItemRequest(
            String placeId,
            String memo
    ) {
    }
}
