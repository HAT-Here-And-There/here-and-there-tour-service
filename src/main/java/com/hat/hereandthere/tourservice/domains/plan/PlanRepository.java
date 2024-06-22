package com.hat.hereandthere.tourservice.domains.plan;

import com.hat.hereandthere.tourservice.domains.plan.entity.Plan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlanRepository extends JpaRepository<Plan, Long> {

    List<Plan> getPlansByUserId(Long userId);
}
