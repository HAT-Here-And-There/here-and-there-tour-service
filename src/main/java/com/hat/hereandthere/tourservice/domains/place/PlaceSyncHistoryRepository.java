package com.hat.hereandthere.tourservice.domains.place;

import com.hat.hereandthere.tourservice.domains.place.entity.PlaceSyncHistory;
import java.sql.Timestamp;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaceSyncHistoryRepository extends JpaRepository<PlaceSyncHistory, Timestamp> {
    Optional<PlaceSyncHistory> findTopByIsSuccessOrderByTimestampDesc(boolean isSuccess);
}
