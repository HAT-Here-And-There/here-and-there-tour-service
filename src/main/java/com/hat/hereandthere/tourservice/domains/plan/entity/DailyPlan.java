package com.hat.hereandthere.tourservice.domains.plan.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity(name = "daily_plan")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DailyPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private LocalDate date;

    @Column
    private int dayNumber;

    @ManyToOne
    @JoinColumn(name = "plan_id")
    private Plan plan;

    @OneToMany(
            mappedBy = "dailyPlan",
            cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DailyPlanItem> dailyPlanItems;

    @Builder
    public DailyPlan(Long id,
                     LocalDate date,
                     int dayNumber,
                     Plan plan) {
        this.id = id;
        this.date = date;
        this.dayNumber = dayNumber;
        this.plan = plan;
    }
}
