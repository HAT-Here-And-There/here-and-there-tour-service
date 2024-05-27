package com.hat.hereandthere.tourservice.domains.area;

import com.hat.hereandthere.tourservice.domains.area.entity.Area;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AreaRepository extends JpaRepository<Area, String> {

}
