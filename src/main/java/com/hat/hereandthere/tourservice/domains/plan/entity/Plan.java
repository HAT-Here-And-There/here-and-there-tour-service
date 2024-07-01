package com.hat.hereandthere.tourservice.domains.plan.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.sql.Date;
import java.util.List;


@Getter
@Entity
public class Plan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Date startDate;

    @Column(nullable = false)
    private Date endDate;

    @Column(nullable = false)
    private Long userId;

    @OneToMany(mappedBy = "plan", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DailyPlan> dailyPlans;

}
