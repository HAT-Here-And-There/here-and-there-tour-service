package com.hat.hereandthere.tourservice.domains.sigungu;

import com.hat.hereandthere.tourservice.domains.sigungu.entity.Sigungu;
import com.hat.hereandthere.tourservice.domains.sigungu.entity.SigunguId;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SigunguRepository extends JpaRepository<Sigungu, SigunguId> {
    @Query(value = "SELECT * FROM sigungu WHERE id = :uniqueId and area_id = :areaId", nativeQuery = true)
    Optional<Sigungu> findByUniqueId(String uniqueId, String areaId);

    @Query("select s from sigungu s join fetch s.majorRegion m where m.id = ?1")
    List<Sigungu> findAllByMajorRegionId(Long majorRegionId);
}
