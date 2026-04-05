package com.suncontrol.core.service.log;

import com.suncontrol.core.constant.common.District;
import com.suncontrol.core.dto.log.DailyWeatherDto;
import com.suncontrol.core.repository.log.DailyWeatherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class DailyWeatherService {
    /// 일일 기상정보 도메인 서비스
    private final DailyWeatherRepository repository;

    public void saveAll(List<DailyWeatherDto> dtos) {
        /// todo
        /// 들어온 컬렉션 객체를 Entity List로 변환하여 저장
    }

    public Map<District, Map<LocalDate, DailyWeatherDto>> findAllByDistrictAndDate
            (LocalDate start, LocalDate end) {
        /// DB 결과값이 없으면 빈 맵으로 반환
        return Collections.emptyMap();

        /// 나온 결과값을 이중 맵으로 매핑
    }
}
