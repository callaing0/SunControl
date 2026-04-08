package com.suncontrol.core.service.log;

import com.suncontrol.core.constant.common.District;
import com.suncontrol.core.dto.log.WeatherLogDto;
import com.suncontrol.core.entity.log.WeatherLog;
import com.suncontrol.core.repository.log.WeatherLogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Slf4j
public class WeatherLogService {
    private final WeatherLogRepository repository;

    @Transactional
    public void saveAll(List<WeatherLogDto> dtos) {
        /// 지역별 기상 API 기록 리스트를 엔티티 리스트로 만들기
        List<WeatherLog> entities = dtos.stream().map(WeatherLog::new).toList();

        /// 저장
        /// TODO : 건수가 많아지면 BATCH 이용하는 방향으로 전환해야 함
        int result = repository.saveAll(entities);

        log.info("{}건 저장 완료", result);
    }

    public Map<District, Map<LocalDateTime, WeatherLogDto>> getMapByDistrictAndTime
            (LocalDateTime start, LocalDateTime end) {
        List<WeatherLog> entities = findLatestLog(start, end);
        /// 결과가 없으면 빈 맵으로 반환
        if(entities.isEmpty()) {
            return Collections.emptyMap();
        }
        /// 레포지토리 결과 List<Entity> -> Map<K1, Map<K2, DTO>>
        return entities.stream().collect(
                Collectors.groupingBy(
                        WeatherLog::getDistrict,
                        Collectors.toMap(
                                WeatherLog::getBaseTime,
                                WeatherLogDto::new
                        )
                )
        );
    }

    public WeatherLogDto findByDistrict(District district, LocalDateTime time) {
        /// todo : 대시보드용 기상 정보 조회용
        /// district와 time 기준 가장 가까운 과거시점의 스냅샷 1건을 반환한다.
        return null;
    }

    private List<WeatherLog>  findLatestLog(LocalDateTime start, LocalDateTime end) {
        return repository.findLatestLogs(start, end);
    }

    public WeatherLogDto getRecentLog() {
        return new WeatherLogDto(repository.findLatestLogOfAll());
    }
}
