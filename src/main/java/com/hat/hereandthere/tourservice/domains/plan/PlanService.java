package com.hat.hereandthere.tourservice.domains.plan;

import com.hat.hereandthere.tourservice.domains.place.PlaceRepository;
import com.hat.hereandthere.tourservice.domains.place.entity.Place;
import com.hat.hereandthere.tourservice.domains.plan.entity.DailyPlan;
import com.hat.hereandthere.tourservice.domains.plan.entity.DailyPlanItem;
import com.hat.hereandthere.tourservice.domains.plan.entity.Plan;
import com.hat.hereandthere.tourservice.domains.plan.model.dto.CreatePlanDto;
import com.hat.hereandthere.tourservice.domains.plan.model.dto.GetPlanDto;
import com.hat.hereandthere.tourservice.domains.plan.repository.DailyPlanItemRepository;
import com.hat.hereandthere.tourservice.domains.plan.repository.DailyPlanRepository;
import com.hat.hereandthere.tourservice.domains.plan.repository.PlanRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@AllArgsConstructor
@Service
public class PlanService {
    private final PlanRepository planRepository;
    private final DailyPlanRepository dailyPlanRepository;
    private final DailyPlanItemRepository dailyPlanItemRepository;
    private final PlaceRepository placeRepository;

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

    public Long createPlan(CreatePlanDto dto) {
        Plan plan = Plan.builder()
                .name(dto.name())
                .startDate(dto.startDate())
                .endDate(dto.endDate())
                .userId(1L)
                .build();

        final Plan newPlan = planRepository.save(plan);

        createDailyPlan(dto.dailyPlans(), newPlan);

        return plan.getId();
    }

    private void createDailyPlan(List<CreatePlanDto.CreatePlanDtoDailyPlan> dailyPlanDtoList, Plan plan) {
        for (int i = 0; i < dailyPlanDtoList.size(); i++) {
            final DailyPlan newDailyPlan = dailyPlanRepository.save(
                    DailyPlan.builder()
                            .date(plan.getStartDate().plusDays(i))
                            .dayNumber(1 + i)
                            .plan(plan)
                            .build()
            );

            createDailyPlanItem(dailyPlanDtoList.get(i).dailyPlanItems(), newDailyPlan);

        }


    }

    private void createDailyPlanItem(List<CreatePlanDto.CreatePlanDtoDailyPlanItem> createPlanDtoDailyPlanItemList,
                                     DailyPlan dailyPlan) {

        List<DailyPlanItem> dailyPlanItems = new ArrayList<>();

        for (int i = createPlanDtoDailyPlanItemList.size() - 1; i >= 0; i--) {
            final CreatePlanDto.CreatePlanDtoDailyPlanItem dtoItem = createPlanDtoDailyPlanItemList.get(i);
            final Optional<Place> optionalPlace = placeRepository.findById(dtoItem.placeId());

            if (optionalPlace.isEmpty()) {
                throw new IllegalArgumentException();
            }

            log.warn("{}", optionalPlace.get());
            Place p = optionalPlace.get();
            log.warn("{}", p.getSigungu());


            final DailyPlanItem newItem = DailyPlanItem.builder()
                    .place(optionalPlace.get())
                    .memo(dtoItem.memo())
                    .nextItem(dailyPlanItems.isEmpty() ? null : dailyPlanItems.getFirst())
                    .dailyPlan(dailyPlan)
                    .build();

            dailyPlanItemRepository.save(newItem);

            dailyPlanItems.addFirst(newItem);
        }
    }

}
