package com.hat.hereandthere.tourservice.domains.plan.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;
import java.util.List;

@Getter
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

    @OneToMany(mappedBy = "dailyPlan")
    @OnDelete(action = OnDeleteAction.CASCADE)
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
