package com.hat.hereandthere.tourservice.domains.plan.model.dto;


public record GetPlanDto(
        Long id,
        String name,
        String startDate,
        String endDate
) {
}
