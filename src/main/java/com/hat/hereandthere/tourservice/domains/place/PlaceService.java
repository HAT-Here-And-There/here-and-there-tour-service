package com.hat.hereandthere.tourservice.domains.place;

import static com.hat.hereandthere.tourservice.common.exception.BaseExceptionStatus.PLACE_NOT_FOUND;

import com.hat.hereandthere.tourservice.common.exception.BaseException;
import com.hat.hereandthere.tourservice.domains.area.AreaService;
import com.hat.hereandthere.tourservice.domains.area.entity.Area;
import com.hat.hereandthere.tourservice.domains.place.entity.Place;
import com.hat.hereandthere.tourservice.domains.place.entity.PlaceSyncHistory;
import com.hat.hereandthere.tourservice.domains.place.model.dto.*;
import com.hat.hereandthere.tourservice.domains.place.model.response.TourApiGetPlacesPageRes;
import com.hat.hereandthere.tourservice.domains.place.model.response.TourApiGetPlacesPageRes.Item;
import com.hat.hereandthere.tourservice.domains.sigungu.SigunguRepository;
import com.hat.hereandthere.tourservice.domains.sigungu.SigunguService;
import jakarta.transaction.Transactional;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Stream;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class PlaceService {
    private final Environment env;
    private final RestTemplate restTemplate;
    private final PlaceRepository placeRepository;
    private final PlaceSyncHistoryRepository placeSyncHistoryRepository;
    private final SigunguRepository sigunguRepository;

    private final WebClient tourApiWebClient;
    private final String DEFAULT_REQUIRED_QUERY_STRING = "MobileOS={MobileOS}&MobileApp={MobileApp}&_type={_type}&serviceKey={serviceKey}";
    private final String MOCK_IMAGE_URL = "https://images.pexels.com/photos/346885/pexels-photo-346885.jpeg?auto=compress&cs=tinysrgb&w=800";
    private final String PLACE_SYNC_PATH = "/areaBasedSyncList1";
    private final Integer NUM_OF_ROWS = 1000;
    private final AreaService areaService;
    private final SigunguService sigunguService;

    public GetPlacesPageDto getPlacesPage(Pageable pageable) {
        Page<Place> placePage = placeRepository.findAll(pageable);
        List<Place> places = placePage.getContent();

        return GetPlacesPageDto.builder()
                .places(places.stream().map(place -> GetPlacesPageDto.Place.builder()
                        .id(place.getId())
                        .name(place.getName())
                        .imageUrl(place.getImageUrl())
                        .build()).toList())
                .totalPages(placePage.getTotalPages())
                .build();
    }

    public GetPlacesPageDto getPlaces(Pageable pageable) {
        log.error("page: {}", pageable.getPageNumber());
        final ChatCountDto result =
                restTemplate.getForObject(String.format("%s/places?page=%d&pageSize=%d", env.getProperty("chat-service.url"), pageable.getPageNumber(), pageable.getPageSize()), ChatCountDto.class);

        if (result == null) {
            return getPlacesPage(pageable);
        }

        final List<ChatCountDto.Item> placeChatCountList = result.placeChatCountList().stream().toList();

        final List<Place> placeListSortedByChatCount = getSortedPlacesByIds(placeChatCountList.stream().map(item -> item.placeId().toString()).toList());
        final Page<Place> firstPage = placeRepository.findAll(pageable.first());
        final List<Place> restPlaces = firstPage.getContent()
                .subList(0, pageable.getPageSize() - placeChatCountList.size())
                .stream()
                .filter(place -> !placeListSortedByChatCount.contains(place))
                .toList();

        return GetPlacesPageDto
                .builder()
                .places(
                        Stream.concat(placeListSortedByChatCount.stream(), restPlaces.stream())
                                .map(place -> GetPlacesPageDto.Place.builder()
                                        .id(place.getId())
                                        .name(place.getName())
                                        .imageUrl(place.getImageUrl())
                                        .chatCount(
                                                Math.toIntExact(placeChatCountList.stream()
                                                        .filter(item -> item.placeId().equals(Long.parseLong(place.getId())))
                                                        .findFirst()
                                                        .map(ChatCountDto.Item::chatCount)
                                                        .orElse(0L))
                                        )
                                        .build()
                                ).toList()
                )
                .totalPages(firstPage.getTotalPages())
                .build();

    }

    public GetPlacesPageDto getPlacesFilteredByMajorRegion(Long majorRegionid, Pageable pageable) {
        final String url = String.format("%s/places?majorRegionId=%d&page=%d&pageSize=%d", env.getProperty("chat-service.url"), majorRegionid, pageable.getPageNumber(), pageable.getPageSize());
        final ChatCountDto result =
                restTemplate.getForObject(url, ChatCountDto.class);

        if (result == null) {
            return getPlacesPage(pageable);
        }

        final List<ChatCountDto.Item> placeChatCountList = result.placeChatCountList().stream().toList();

        final List<Place> placeListSortedByChatCount = getSortedPlacesByIds(placeChatCountList.stream().map(item -> item.placeId().toString()).toList());
        final Page<Place> firstPage = getPlacesPageDtoByMajorRegionId(majorRegionid, pageable);
        final List<Place> restPlaces = firstPage.getContent()
                .subList(0, pageable.getPageSize() - placeChatCountList.size())
                .stream()
                .filter(place -> !placeListSortedByChatCount.contains(place))
                .toList();


        return GetPlacesPageDto
                .builder()
                .places(
                        Stream.concat(placeListSortedByChatCount.stream(), restPlaces.stream())
                                .map(place -> GetPlacesPageDto.Place.builder()
                                        .id(place.getId())
                                        .name(place.getName())
                                        .imageUrl(place.getImageUrl())
                                        .chatCount(
                                                Math.toIntExact(placeChatCountList.stream()
                                                        .filter(item -> item.placeId().equals(Long.parseLong(place.getId())))
                                                        .findFirst()
                                                        .map(ChatCountDto.Item::chatCount)
                                                        .orElse(0L))
                                        )
                                        .build()
                                ).toList()
                )
                .totalPages(firstPage.getTotalPages())
                .build();
    }

    public GetPlacesPageDto getPlacesFilteredBySigungu(String areaId, String sigunguId, Pageable pageable) {
        final String url = String.format("%s/places?areaId=%s&sigunguId=%s&page=%d&pageSize=%d", env.getProperty("chat-service.url"), areaId, sigunguId, pageable.getPageNumber(), pageable.getPageSize());

        final ChatCountDto result = restTemplate.getForObject(url, ChatCountDto.class);

        if (result == null) {
            return getPlacesPage(pageable);
        }

        final List<ChatCountDto.Item> placeChatCountList = result.placeChatCountList().stream().toList();

        final List<Place> placeListSortedByChatCount = getSortedPlacesByIds(placeChatCountList.stream().map(item -> item.placeId().toString()).toList());
        final Page<Place> firstPage = placeRepository.findAllBySigungu(areaId, sigunguId, pageable);
        final List<Place> restPlaces = firstPage.getContent()
                .subList(0, pageable.getPageSize() - placeChatCountList.size())
                .stream()
                .filter(place -> !placeListSortedByChatCount.contains(place))
                .toList();

        return GetPlacesPageDto
                .builder()
                .places(
                        Stream.concat(placeListSortedByChatCount.stream(), restPlaces.stream())
                                .map(place -> GetPlacesPageDto.Place.builder()
                                        .id(place.getId())
                                        .name(place.getName())
                                        .imageUrl(place.getImageUrl())
                                        .chatCount(
                                                Math.toIntExact(placeChatCountList.stream()
                                                        .filter(item -> item.placeId().equals(Long.parseLong(place.getId())))
                                                        .findFirst()
                                                        .map(ChatCountDto.Item::chatCount)
                                                        .orElse(0L))
                                        )
                                        .build()
                                ).toList()
                )
                .totalPages(firstPage.getTotalPages())
                .build();
    }

    private List<Place> getSortedPlacesByIds(List<String> placeIds) {
        final List<Place> queriedPlaces = placeRepository.findPlacesByIdIn(placeIds);

        queriedPlaces.sort(Comparator.comparingInt(place -> placeIds.indexOf(place.getId())));

        return queriedPlaces;
    }


    private Page<Place> getPlacesPageDtoByMajorRegionId(Long majorRegionId, Pageable pageable) {
        final List<com.hat.hereandthere.tourservice.domains.sigungu.entity.Sigungu> sigunguList = sigunguRepository.findAllByMajorRegionId(majorRegionId);
        return placeRepository.findAllBySigunguIn(sigunguList, pageable);
    }

    public GetPlaceDetailDto getPlaceDetail(String placeId) {
        Optional<Place> place = placeRepository.findById(placeId);

        if (place.isEmpty()) {
            throw new BaseException(PLACE_NOT_FOUND);
        }

        return GetPlaceDetailDto.builder()
                .id(place.get().getId())
                .name(place.get().getName())
                .imageUrl(place.get().getImageUrl())
                .contact(place.get().getContact())
                .openingHours(place.get().getOpeningHours())
                .closingHours(place.get().getClosingHours())
                .area(GetPlaceDetailDto.Area.builder()
                        .id(place.get().getSigungu().getArea().getId())
                        .name(place.get().getSigungu().getArea().getName())
                        .build())
                .sigungu(GetPlaceDetailDto.Sigungu.builder()
                        .id(place.get().getSigungu().getId())
                        .name(place.get().getSigungu().getName())
                        .build())
                .address(place.get().getAddress())
                .build();
    }

    public GetPlaceMetaDto getPlaceMetadata(String placeId) {
        Optional<Place> optionalPlace = placeRepository.findById(placeId);

        if (optionalPlace.isEmpty()) {
            throw new IllegalArgumentException("Wrong place id");
        }

        return GetPlaceMetaDto.builder()
                .placeId(placeId)
                .majorRegionId(optionalPlace.get().getSigungu().getMajorRegion().getId())
                .areaId(optionalPlace.get().getSigungu().getArea().getId())
                .sigunguId(optionalPlace.get().getSigungu().getId())
                .build();
    }

    public void syncPlace() {
        Optional<PlaceSyncHistory> latestHistory = placeSyncHistoryRepository.findTopByIsSuccessOrderByTimestampDesc(
                true);

        // 처음인 경우
        if (latestHistory.isEmpty()) {
            syncNextInitialPlace(1);
            return;
        }
        // 첫 동기화 중간인 경우
        if (latestHistory.get().getIsInitialSync() && !latestHistory.get().getIsLastPage()) {
            syncNextInitialPlace(latestHistory.get().getPageNumber() + 1);
            return;
        }

        Date now = new Date();
        String historyString = new SimpleDateFormat("yyyyMMdd").format(latestHistory.get().getTimestamp());
        String nowString = new SimpleDateFormat("yyyyMMdd").format(now);
        // 첫 동기화 종료한 경우
        if (latestHistory.get().getIsInitialSync() && latestHistory.get().getIsLastPage()) {
            syncNextPlace(1, nowString);
            return;
        }
        // 중간 동기화 중인 경우
        if (!latestHistory.get().getIsInitialSync() && !latestHistory.get().getIsLastPage()) {
            syncNextPlace(latestHistory.get().getPageNumber() + 1, nowString);
            return;
        }
        // 오늘의 중간 동기화 종료한 경우
        if (historyString.equals(nowString) && latestHistory.get().getIsLastPage()) {
            return;
        }
        // 첫 중간 동기화인 경우
        if (!historyString.equals(nowString) && latestHistory.get().getIsLastPage()) {
            syncNextPlace(1, nowString);
        }
    }

    public void syncNextInitialPlace(Integer pageNumber) {
        TourApiGetPlacesPageRes response = tourApiWebClient.get()
                .uri(UriBuilder -> UriBuilder
                        .path(PLACE_SYNC_PATH)
                        .query(DEFAULT_REQUIRED_QUERY_STRING)
                        .queryParam("numOfRows", NUM_OF_ROWS)
                        .queryParam("contentTypeId", 12)
                        .queryParam("pageNo", pageNumber)
                        .build())
                .retrieve()
                .bodyToMono(TourApiGetPlacesPageRes.class)
                .block();

        assert response != null;
        List<GetPlaceswithPageDto> places;
        if (response.getResponse().getHeader().getResultCode().equals("0000")) {
            List<Item> items = response.getResponse().getBody().getItems().getItem();
            places = items.stream().map(item -> GetPlaceswithPageDto.builder()
                    .addr1(item.getAddr1())
                    .areacode(item.getAreacode())
                    .contentid(item.getContentid())
                    .contenttypeid(item.getContenttypeid())
                    .firstimage(item.getFirstimage())
                    .firstimage2(item.getFirstimage2())
                    .modifiedtime(item.getModifiedtime())
                    .sigungucode(item.getSigungucode())
                    .tel(item.getTel())
                    .title(item.getTitle())
                    .build()).toList();

            savePlaces(places);

            if (places.size() < NUM_OF_ROWS) {
                saveSuccessHistory(pageNumber, true, true);
                return;
            }

            saveSuccessHistory(pageNumber, true, false);
            return;
        }

        saveFailedHistory(response.getResponse().getHeader().getResultMsg(), true);
    }

    public void syncNextPlace(Integer pageNumber, String date) {
        TourApiGetPlacesPageRes response = tourApiWebClient.get()
                .uri(UriBuilder -> UriBuilder
                        .path(PLACE_SYNC_PATH)
                        .query(DEFAULT_REQUIRED_QUERY_STRING)
                        .queryParam("numOfRows", NUM_OF_ROWS)
                        .queryParam("contentTypeId", 12)
                        .queryParam("pageNo", pageNumber)
                        .queryParam("modifiedtime", date)
                        .build())
                .retrieve()
                .bodyToMono(TourApiGetPlacesPageRes.class)
                .block();

        List<GetPlaceswithPageDto> places;
        assert response != null;
        if (response.getResponse().getHeader().getResultCode().equals("0000")) {
            List<Item> items = response.getResponse().getBody().getItems().getItem();
            places = items.stream().map(item -> GetPlaceswithPageDto.builder()
                    .addr1(item.getAddr1())
                    .areacode(item.getAreacode())
                    .contentid(item.getContentid())
                    .contenttypeid(item.getContenttypeid())
                    .firstimage(item.getFirstimage())
                    .firstimage2(item.getFirstimage2())
                    .modifiedtime(item.getModifiedtime())
                    .sigungucode(item.getSigungucode())
                    .tel(item.getTel())
                    .title(item.getTitle())
                    .build()).toList();

            savePlaces(places);

            if (places.size() < NUM_OF_ROWS) {
                saveSuccessHistory(pageNumber, false, true);
                return;
            }

            saveSuccessHistory(pageNumber, false, false);
            return;
        }

        saveFailedHistory(response.getResponse().getHeader().getResultMsg(), false);
    }

    public void saveSuccessHistory(Integer pageNumber, Boolean isInitialSync, Boolean isLastPage) {
        placeSyncHistoryRepository.save(PlaceSyncHistory.builder()
                .timestamp(new Timestamp(System.currentTimeMillis()))
                .pageNumber(pageNumber)
                .isSuccess(true)
                .isLastPage(isLastPage)
                .isInitialSync(isInitialSync)
                .build());
    }

    public void saveFailedHistory(String responseMessage, Boolean isInitialSync) {
        placeSyncHistoryRepository.save(PlaceSyncHistory.builder()
                .timestamp(new Timestamp(System.currentTimeMillis()))
                .isInitialSync(isInitialSync)
                .isSuccess(false)
                .responseMsg(responseMessage)
                .build());
    }

    private void savePlaces(List<GetPlaceswithPageDto> places) {
        Area area;
        com.hat.hereandthere.tourservice.domains.sigungu.entity.Sigungu sigungu;
        for (GetPlaceswithPageDto place : places) {
            try {
                area = areaService.getAreaById(place.getAreacode());
                sigungu = sigunguService.getSigunguById(place.getSigungucode(), area.getId());
            } catch (BaseException e) {
                log.info(e.getMessage());
                continue;
            }

            placeRepository.save(Place.builder()
                    .id(place.getContentid())
                    .imageUrl(place.getFirstimage() == null || place.getFirstimage().isEmpty() ? MOCK_IMAGE_URL : place.getFirstimage())
                    .name(place.getTitle())
                    .contact(place.getTel())
                    .openingHours(Time.valueOf("08:00:00"))
                    .closingHours(Time.valueOf("18:00:00"))
                    .address(place.getAddr1())
                    .sigungu(sigungu)
                    .build());
        }
    }
}
