package com.hat.hereandthere.tourservice.domains.sigungu;

import static com.hat.hereandthere.tourservice.common.exception.BaseExceptionStatus.SIGUNGU_NOT_FOUND;

import com.hat.hereandthere.tourservice.common.exception.BaseException;
import com.hat.hereandthere.tourservice.domains.sigungu.entity.Sigungu;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SigunguService {
    private final SigunguRepository sigunguRepository;

    public Sigungu getSigunguById(String sigunguId, String areaId) {
        return sigunguRepository.findByUniqueId(sigunguId, areaId).orElseThrow(() -> new BaseException(SIGUNGU_NOT_FOUND));
    }
}
