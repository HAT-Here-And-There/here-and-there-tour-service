package com.hat.hereandthere.tourservice.domains.plan.model.request;

import java.util.List;

public record PatchPlanRequest(
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
