package com.hat.hereandthere.tourservice.domains.place.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.sql.Timestamp;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "place_sync_history")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PlaceSyncHistory {
    @Id
    private Timestamp timestamp;

    @Column(name = "page_number")
    private Integer pageNumber;

    @Column(name = "response_msg", columnDefinition = "TEXT")
    private String responseMsg;

    @Column(name = "is_success")
    private Boolean isSuccess;

    @Column(name = "is_initial_sync")
    private Boolean isInitialSync;

    @Column(name = "is_last_page")
    private Boolean isLastPage;

    @Builder
    public PlaceSyncHistory(Timestamp timestamp, Integer pageNumber, String responseMsg, Boolean isSuccess, Boolean isInitialSync, Boolean isLastPage) {
        this.timestamp = timestamp;
        this.pageNumber = pageNumber;
        this.responseMsg = responseMsg;
        this.isSuccess = isSuccess;
        this.isInitialSync = isInitialSync;
        this.isLastPage = isLastPage;
    }
}
