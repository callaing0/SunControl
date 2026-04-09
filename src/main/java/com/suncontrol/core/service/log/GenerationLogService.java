package com.suncontrol.core.service.log;

import com.suncontrol.core.dto.log.GenerationLogDto;
import com.suncontrol.core.dto.log.LastGeneratedTime;
import com.suncontrol.core.entity.log.GenerationLog;
import com.suncontrol.core.repository.log.GenerationLogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class GenerationLogService {
    ///  발전기록 raw 데이터 CRUD 도메인 서비스

    private final GenerationLogRepository repository;

    ///  인버터 별 최근 발전 생성시각 찾아오기
    public Map<Long, LocalDateTime> getLastGeneratedTimeByAllInverters() {
        return repository.findLastsOf().stream()
                .collect(Collectors.toMap(
                        LastGeneratedTime::getPlantId,
                        LastGeneratedTime::getBaseTime
                ));
    }

    public void saveAll(List<GenerationLogDto> results) {
        if(results == null || results.isEmpty()) {
            log.warn("No results found");
            return;
        }
        int result = repository.saveAll(
                results.stream().map(GenerationLog::new).toList());

        log.info("Save {} results to DB", result);
    }
}
