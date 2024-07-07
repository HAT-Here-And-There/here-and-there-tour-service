package com.hat.hereandthere.tourservice.domains.plan.model.dto;


import java.time.LocalDate;

public record GetPlanDto(
        Long id,
        String name,
        LocalDate startDate,
        LocalDate endDate
) {
}
