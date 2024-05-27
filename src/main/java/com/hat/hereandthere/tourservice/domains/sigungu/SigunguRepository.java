package com.hat.hereandthere.tourservice.domains.sigungu;

import com.hat.hereandthere.tourservice.domains.sigungu.entity.Sigungu;
import com.hat.hereandthere.tourservice.domains.sigungu.entity.SigunguId;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SigunguRepository extends JpaRepository<Sigungu, SigunguId> {
    @Query(value = "SELECT * FROM sigungu WHERE id = :uniqueId and area_id = :areaId", nativeQuery = true)
    Optional<Sigungu> findByUniqueId(String uniqueId, String areaId);
}
