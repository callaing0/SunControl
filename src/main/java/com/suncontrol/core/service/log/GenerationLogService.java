package com.suncontrol.core.service.log;

import com.suncontrol.core.dto.log.GenerationLogDto;
import com.suncontrol.core.entity.log.GenerationLog;
import com.suncontrol.core.repository.log.GenerationLogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class GenerationLogService {
    ///  발전기록 raw 데이터 CRUD 도메인 서비스

    private final GenerationLogRepository repository;

    public Map<Long, LocalDateTime> getLastGeneratedTimeByAllInverters() {
        return Collections.emptyMap();
    }

    public void saveAll(List<GenerationLogDto> results) {
        int result = repository.saveAll(
                results.stream().map(GenerationLog::new).toList());

    }
}
