package com.hat.hereandthere.tourservice.domains.plan.repository;

import com.hat.hereandthere.tourservice.domains.plan.entity.DailyPlanItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DailyPlanItemRepository extends JpaRepository<DailyPlanItem, Long> {
}
