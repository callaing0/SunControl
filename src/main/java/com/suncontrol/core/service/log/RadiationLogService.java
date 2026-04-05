package com.suncontrol.core.service.log;

import com.suncontrol.core.dto.log.RadiationLogDto;
import com.suncontrol.core.repository.log.RadiationLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class RadiationLogService {
    /// 일시량 기록 도메인 서비스
    private final RadiationLogRepository repository;

    public void saveAll(Map<Long, List<RadiationLogDto>> dtoMap) {
        /// todo
    }

    public Map<Long, Map<LocalDateTime, RadiationLogDto>> findAllByPlantAndTime
            (LocalDateTime start, LocalDateTime end) {
        /// DB가 비어있으면 null이 아닌 빈 맵으로 반환
        return Collections.emptyMap();
        /// todo
        ///
    }
}
