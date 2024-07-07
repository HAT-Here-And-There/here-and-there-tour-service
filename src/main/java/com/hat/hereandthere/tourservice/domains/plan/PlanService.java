package com.hat.hereandthere.tourservice.domains.plan;

import com.hat.hereandthere.tourservice.domains.place.PlaceRepository;
import com.hat.hereandthere.tourservice.domains.place.entity.Place;
import com.hat.hereandthere.tourservice.domains.plan.entity.DailyPlan;
import com.hat.hereandthere.tourservice.domains.plan.entity.DailyPlanItem;
import com.hat.hereandthere.tourservice.domains.plan.entity.Plan;
import com.hat.hereandthere.tourservice.domains.plan.model.dto.CreatePlanDto;
import com.hat.hereandthere.tourservice.domains.plan.model.dto.GetPlanDetailDto;
import com.hat.hereandthere.tourservice.domains.plan.model.dto.GetPlanDto;
import com.hat.hereandthere.tourservice.domains.plan.model.request.PatchPlanRequest;
import com.hat.hereandthere.tourservice.domains.plan.repository.DailyPlanItemRepository;
import com.hat.hereandthere.tourservice.domains.plan.repository.DailyPlanRepository;
import com.hat.hereandthere.tourservice.domains.plan.repository.PlanRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@AllArgsConstructor
@Service
public class PlanService {
    private final PlanRepository planRepository;
    private final DailyPlanRepository dailyPlanRepository;
    private final DailyPlanItemRepository dailyPlanItemRepository;
    private final PlaceRepository placeRepository;

    public List<GetPlanDto> getUserPlans(Long userId) {
        return planRepository
                .getPlansByUserId(userId)
                .stream().map(e -> new GetPlanDto(
                                e.getId(),
                                e.getName(),
                                e.getStartDate(),
                                e.getEndDate()
                        )
                ).toList();

    }

    @Transactional
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

    public GetPlanDetailDto getPlan(Long planId) {
        final Optional<Plan> optionalPlan = planRepository.findById(planId);

        if (optionalPlan.isEmpty()) {
            throw new IllegalArgumentException();
        }

        final Plan plan = optionalPlan.get();

        return new GetPlanDetailDto(
                plan.getId(),
                plan.getName(),
                plan.getStartDate(),
                plan.getEndDate(),
                plan
                        .getDailyPlans()
                        .stream()
                        .map(e -> new GetPlanDetailDto.DailyPlanDto(
                                        e.getId(),
                                        e.getDate(),
                                        e.getDayNumber(),
                                        sortDailyPlanItems(e.getDailyPlanItems())
                                )
                        ).toList()
        );
    }

    @Transactional
    public void updatePlan(Long planId, PatchPlanRequest patchPlanDto) {
        final Optional<Plan> optionalPlan = planRepository.findById(planId);

        if (optionalPlan.isEmpty()) {
            throw new IllegalArgumentException();
        }

        final Plan plan = optionalPlan.get();

        dailyPlanRepository.deleteAllInBatch(plan.getDailyPlans());

        createDailyPlan(
                patchPlanDto
                        .dailyPlans()
                        .stream()
                        .map(e -> new CreatePlanDto.CreatePlanDtoDailyPlan(
                                e.dailyPlanItems().stream().map(i -> new CreatePlanDto.CreatePlanDtoDailyPlanItem(
                                        i.placeId(),
                                        i.memo()
                                )).toList()
                        ))
                        .toList(),
                plan
        );
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

            final Place place = optionalPlace.get();

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


    private List<GetPlanDetailDto.DailyPlanItemDto> sortDailyPlanItems(List<DailyPlanItem> dailyPlanItems) {
        final List<GetPlanDetailDto.DailyPlanItemDto> sortedDailyPlanItems = new ArrayList<>();

        for (DailyPlanItem dailyPlanItem : dailyPlanItems) {
            int index = 0;
            final boolean isAlreadyAdded = sortedDailyPlanItems
                    .stream()
                    .map(GetPlanDetailDto.DailyPlanItemDto::id)
                    .toList()
                    .contains(dailyPlanItem.getId());

            if (isAlreadyAdded) {
                continue;
            }

            do {
                sortedDailyPlanItems.add(index, new GetPlanDetailDto.DailyPlanItemDto(
                        dailyPlanItem.getId(),
                        dailyPlanItem.getMemo(),
                        new GetPlanDetailDto.DailyPlanItemPlaceDto(
                                dailyPlanItem.getPlace().getId(),
                                dailyPlanItem.getPlace().getName(),
                                dailyPlanItem.getPlace().getImageUrl()
                        )
                ));

                dailyPlanItem = dailyPlanItem.getNextItem();
                index++;


            } while (dailyPlanItem != null && !sortedDailyPlanItems
                    .stream()
                    .map(GetPlanDetailDto.DailyPlanItemDto::id)
                    .toList()
                    .contains(dailyPlanItem.getId())
            );
        }

        return sortedDailyPlanItems;
    }
}
