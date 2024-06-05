package com.hat.hereandthere.tourservice.domains.place;

import com.hat.hereandthere.tourservice.domains.place.entity.Place;
import com.hat.hereandthere.tourservice.domains.place.entity.PlaceId;
import com.hat.hereandthere.tourservice.domains.sigungu.entity.Sigungu;
import java.util.Collection;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PlaceRepository extends JpaRepository<Place, PlaceId>{
    @Query("select p from place p join fetch p.sigungu s join fetch s.majorRegion")
    Page<Place> findAll(Pageable pageable);

    Optional<Place> findById(String placeId);

    Page<Place> findAllBySigunguIn(Collection<Sigungu> sigungu, Pageable pageable);

    @Query("select p from place p join fetch p.sigungu s where s.area.id = ?1 and s.id = ?2")
    Page<Place> findAllBySigungu(String areaId, String sigunguId, Pageable pageable);
}
