package com.hat.hereandthere.tourservice.domains.plan.entity;

import com.hat.hereandthere.tourservice.domains.place.entity.Place;
import jakarta.persistence.*;

@Entity
public class DailyPlanItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "place_id", referencedColumnName = "id")
    private Place place;

    @Column(columnDefinition = "text")
    private String memo;

    @ManyToOne
    @JoinColumn(name = "daily_plan_id")
    private DailyPlan dailyPlan;

    @OneToOne
    @JoinColumn(name = "next")
    private DailyPlanItem nextItem;
}
