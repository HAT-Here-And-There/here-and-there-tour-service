package com.hat.hereandthere.tourservice.domains.likedplace;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("liked-place")
public class LikedPlaceController {
    @GetMapping
    public ResponseEntity<Object> getLikedPlaces() {
        return ResponseEntity.ok("");
    }

    @PostMapping
    public ResponseEntity<Void> likePlace() {
        return ResponseEntity.ok(null);
    }

    @DeleteMapping
    public ResponseEntity<Void> unlikePlace() {
        return ResponseEntity.ok(null);
    }
}
