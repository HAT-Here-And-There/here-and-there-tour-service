package com.hat.hereandthere.tourservice.domains.place;

import com.hat.hereandthere.tourservice.domains.place.entity.Place;
import com.hat.hereandthere.tourservice.domains.place.entity.PlaceId;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaceRepository extends JpaRepository<Place, PlaceId>{
    Page<Place> findAll(Pageable pageable);
    Optional<Place> findById(Long placeId);
}
