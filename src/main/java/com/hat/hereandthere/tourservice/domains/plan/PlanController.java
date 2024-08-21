package com.hat.hereandthere.tourservice.domains.plan;

import com.hat.hereandthere.tourservice.domains.plan.model.dto.CreatePlanDto;
import com.hat.hereandthere.tourservice.domains.plan.model.dto.GetPlanDetailDto;
import com.hat.hereandthere.tourservice.domains.plan.model.dto.GetPlanDto;
import com.hat.hereandthere.tourservice.domains.plan.model.request.PatchPlanRequest;
import com.hat.hereandthere.tourservice.domains.plan.model.request.PostPlanRequest;
import com.hat.hereandthere.tourservice.domains.plan.model.response.PostPlanResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/plan")
@RequiredArgsConstructor
public class PlanController {
    private final PlanService planService;

    @GetMapping
    public ResponseEntity<List<GetPlanDto>> getPlans() {
        return ResponseEntity.ok(planService.getUserPlans(1L));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetPlanDetailDto> getPlan(@PathVariable Long id) {
        return ResponseEntity.ok(planService.getPlan(id));
    }

    @PostMapping
    public ResponseEntity<PostPlanResponse> postPlan(@RequestBody PostPlanRequest request) {
        final Long createdPlanId = planService.createPlan(
                new CreatePlanDto(
                        request.name(),
                        request.startDate(),
                        request.endDate(),
                        request.dailyPlans().stream().map(e -> new CreatePlanDto.CreatePlanDtoDailyPlan(
                                        e.dailyPlanItems().stream().map(i -> new CreatePlanDto.CreatePlanDtoDailyPlanItem(
                                                i.placeId(),
                                                i.memo()
                                        )).toList()
                                )
                        ).toList()
                )
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(new PostPlanResponse(createdPlanId));
    }

    @PatchMapping("/{id}/daily-plans")
    public ResponseEntity<Object> patchPlan(@PathVariable Long id, @RequestBody PatchPlanRequest request) {
        planService.updatePlan(id, request);
        return ResponseEntity.ok().build();
    }

}
