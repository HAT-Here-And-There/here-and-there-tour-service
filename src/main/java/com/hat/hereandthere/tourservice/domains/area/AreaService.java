package com.hat.hereandthere.tourservice.domains.area;

import static com.hat.hereandthere.tourservice.common.exception.BaseExceptionStatus.AREA_NOT_FOUND;

import com.hat.hereandthere.tourservice.common.exception.BaseException;
import com.hat.hereandthere.tourservice.domains.area.entity.Area;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AreaService {
    private final AreaRepository areaRepository;

    public Area getAreaById(String areaId) {
        return areaRepository.findById(areaId).orElseThrow(() -> new BaseException(AREA_NOT_FOUND));
    }
}
