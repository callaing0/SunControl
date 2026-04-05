package com.suncontrol.core.service.log;

import com.suncontrol.core.dto.log.GenerationResultDto;
import com.suncontrol.core.repository.log.GenerationLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class GenerationLogService {
    ///  발전기록 raw 데이터 CRUD 도메인 서비스

    private final GenerationLogRepository repository;

    public Map<Long, LocalDateTime> getLastGeneratedTimeByAllInverters() {
        return Collections.emptyMap();
    }

    public void saveAll(Map<Long, List<GenerationResultDto>> results) {
    }
}
