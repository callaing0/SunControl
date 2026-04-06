package com.suncontrol.core.service.log;

import com.suncontrol.core.constant.common.District;
import com.suncontrol.core.dto.log.DailyWeatherDto;
import com.suncontrol.core.entity.log.DailyWeather;
import com.suncontrol.core.repository.log.DailyWeatherRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class DailyWeatherService {
    /// 일일 기상정보 도메인 서비스
    private final DailyWeatherRepository repository;

    @Transactional
    public void saveAll(List<DailyWeatherDto> dtos) {
        /// 들어온 컬렉션 객체를 Entity List로 변환하여 저장
        /// dto 리스트를 entity 리스트로 변환
        List<DailyWeather> entities = dtos.stream().map(DailyWeather::new).toList();

        /// entity리스트를 DB에 저장
        int result = repository.saveAll(entities);

        /// 로그출력
        log.info("{}건의 일일 기상데이터 저장 완료", result);
    }

    public Map<District, Map<LocalDate, DailyWeatherDto>> findAllByDistrictAndDate
            (LocalDate start, LocalDate end) {
        /// DB 결과값이 없으면 빈 맵으로 반환
        return Collections.emptyMap();

        /// 나온 결과값을 이중 맵으로 매핑
    }
}
