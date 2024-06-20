package com.hat.hereandthere.tourservice.domains.likedplace;

import com.hat.hereandthere.tourservice.domains.likedplace.model.dto.LikedPlaceDto;
import com.hat.hereandthere.tourservice.domains.likedplace.model.request.LikedPlaceRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("liked-place")
public class LikedPlaceController {

    final private LikedPlaceService likedPlaceService;

    @GetMapping
    public ResponseEntity<List<LikedPlaceDto>> getLikedPlaces() {
        return ResponseEntity.ok(
                likedPlaceService.getLikedPlace(1L)
        );
    }

    @PostMapping
    public ResponseEntity<Void> likePlace(@RequestBody LikedPlaceRequest requestBody) {
        likedPlaceService.likePlace(1L, requestBody.id());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping
    public ResponseEntity<Void> unlikePlace(@RequestBody LikedPlaceRequest requestBody) {
        likedPlaceService.unlikePlace(1L, requestBody.id());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
