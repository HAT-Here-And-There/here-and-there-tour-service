package com.hat.hereandthere.tourservice.domains.plan.repository;

import com.hat.hereandthere.tourservice.domains.plan.entity.DailyPlan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DailyPlanRepository extends JpaRepository<DailyPlan, Long> {
}
