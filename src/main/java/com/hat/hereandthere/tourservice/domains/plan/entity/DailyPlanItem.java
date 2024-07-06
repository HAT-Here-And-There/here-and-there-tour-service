package com.hat.hereandthere.tourservice.domains.plan.entity;

import com.hat.hereandthere.tourservice.domains.place.entity.Place;
import jakarta.persistence.*;
import lombok.*;


@Entity(name = "daily_plan_item")
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@Getter
@ToString
public class DailyPlanItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne(targetEntity = Place.class)
    private Place place;

    @Column(columnDefinition = "text")
    private String memo;

    @ManyToOne
    @JoinColumn(name = "daily_plan_id")
    private DailyPlan dailyPlan;

    @OneToOne()
    @JoinColumn(name = "next")
    private DailyPlanItem nextItem;


    @Builder
    public DailyPlanItem(
            Long id,
            Place place,
            String memo,
            DailyPlan dailyPlan,
            DailyPlanItem nextItem
    ) {
        this.id = id;
        this.place = place;
        this.memo = memo;
        this.dailyPlan = dailyPlan;
        this.nextItem = nextItem;
    }
}
