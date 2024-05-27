package com.hat.hereandthere.tourservice.domains.place;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PlaceScheduler {
    private final PlaceService placeService;

    @Scheduled(fixedDelay = 1000 * 60)
    public void syncPlace() {
        placeService.syncPlace();
    }
}
