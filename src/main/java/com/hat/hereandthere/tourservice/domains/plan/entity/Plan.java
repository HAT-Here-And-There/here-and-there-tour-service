package com.hat.hereandthere.tourservice.domains.plan.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;
import java.util.List;


@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Plan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;

    @Column(nullable = false)
    private Long userId;

    @OneToMany(mappedBy = "plan")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<DailyPlan> dailyPlans;


}
