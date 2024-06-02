package com.hat.hereandthere.tourservice.domains.majorregion;

import com.hat.hereandthere.tourservice.domains.majorregion.entity.MajorRegion;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MajorRegionRepository extends JpaRepository<MajorRegion, Long> {
  @Query("select m from major_region m join fetch m.sigungus s join fetch s.area a order by m.id")
  List<MajorRegion> findAllFetchJoin();
}
