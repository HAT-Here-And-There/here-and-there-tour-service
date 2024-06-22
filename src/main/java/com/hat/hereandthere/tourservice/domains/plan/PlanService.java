package com.hat.hereandthere.tourservice.domains.plan;

import com.hat.hereandthere.tourservice.domains.plan.model.dto.GetPlanDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.List;

@AllArgsConstructor
@Service
public class PlanService {
    private final PlanRepository planRepository;

    public List<GetPlanDto> getUserPlans(Long userId) {
        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return planRepository
                .getPlansByUserId(userId)
                .stream().map(e -> new GetPlanDto(
                                e.getId(),
                                e.getName(),
                                simpleDateFormat.format(e.getStartDate()),
                                simpleDateFormat.format(e.getEndDate())
                        )
                ).toList();

    }
}
