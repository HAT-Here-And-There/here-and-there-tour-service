package com.hat.hereandthere.tourservice.domains.likedplace;

import com.hat.hereandthere.tourservice.domains.likedplace.entity.LikedPlace;
import com.hat.hereandthere.tourservice.domains.likedplace.entity.LikedPlaceId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LikedPlaceRepository extends JpaRepository<LikedPlace, LikedPlaceId> {
    List<LikedPlace> findAllByUserIdOrderByLikedAtDesc(Long userId);
}
