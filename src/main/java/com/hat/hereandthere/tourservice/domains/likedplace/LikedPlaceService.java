package com.hat.hereandthere.tourservice.domains.likedplace;

import com.hat.hereandthere.tourservice.common.exception.BaseException;
import com.hat.hereandthere.tourservice.common.exception.BaseExceptionStatus;
import com.hat.hereandthere.tourservice.domains.likedplace.entity.LikedPlace;
import com.hat.hereandthere.tourservice.domains.likedplace.model.dto.LikedPlaceDto;
import com.hat.hereandthere.tourservice.domains.place.PlaceRepository;
import com.hat.hereandthere.tourservice.domains.place.entity.Place;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class LikedPlaceService {
    private final LikedPlaceRepository likedPlaceRepository;
    private final PlaceRepository placeRepository;

    public void likePlace(Long userId, String placeId) {
        final Place place = getPlaceOrThrow(placeId);
        final LikedPlace likedPlace = LikedPlace.builder()
                .userId(userId)
                .place(place)
                .build();

        likedPlaceRepository.save(likedPlace);
    }

    public void unlikePlace(Long userId, String placeId) {
        final Place place = getPlaceOrThrow(placeId);
        final LikedPlace likedPlace = LikedPlace.builder()
                .userId(userId)
                .place(place)
                .build();

        likedPlaceRepository.delete(likedPlace);
    }

    public List<LikedPlaceDto> getLikedPlace(Long userId) {
        return likedPlaceRepository.findAllByUserIdOrderByLikedAtDesc(userId)
                .stream()
                .map(e -> {
                    final Place place = e.getPlace();
                    return new LikedPlaceDto(
                            place.getId(),
                            place.getName(),
                            place.getImageUrl()
                    );
                }).toList();
    }

    private Place getPlaceOrThrow(String placeId) {
        Optional<Place> optionalPlace = placeRepository.findById(placeId);

        if (optionalPlace.isEmpty()) {
            throw new BaseException(BaseExceptionStatus.PLACE_NOT_FOUND);
        }

        return optionalPlace.get();
    }

}
