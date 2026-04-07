package com.suncontrol.core.service.log;

import com.suncontrol.core.dto.log.RadiationLogDto;
import com.suncontrol.core.entity.log.RadiationLog;
import com.suncontrol.core.repository.log.RadiationLogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class RadiationLogService {
    /// 일시량 기록 도메인 서비스
    private final RadiationLogRepository repository;

    @Transactional
    public void saveAll(List<RadiationLogDto> dtoList) {
        /// 파라미터로 받은 dto를 entity로 변환
        List<RadiationLog> entities = dtoList.stream().map(RadiationLog::new).toList();

        /// 변환한 entity를 저장
        int result = repository.saveAll(entities);

        /// 콘솔출력
        log.info("{}건 저장 완료", result);
    }

    public Map<Long, Map<LocalDateTime, RadiationLogDto>> getMapByPlantIdAndTime
            (LocalDateTime start, LocalDateTime end) {
        List<RadiationLog> entities = findLatestLog(start, end);
        /// DB가 비어있으면 null이 아닌 빈 맵으로 반환
        if(entities.isEmpty()) {
            return Collections.emptyMap();
        }

        return entities.stream().collect(
                Collectors.groupingBy(
                        RadiationLog::getPlantId,
                        Collectors.toMap(
                                RadiationLog::getBaseTime,
                                RadiationLogDto::new
                        )
                )
        );
    }

    private List<RadiationLog> findLatestLog(LocalDateTime start, LocalDateTime end) {
        return repository.findLatestLogs(start, end);
    }
}
