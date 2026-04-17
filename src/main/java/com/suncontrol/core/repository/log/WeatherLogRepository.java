package com.suncontrol.core.repository.log;

import com.suncontrol.core.entity.log.WeatherLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface WeatherLogRepository {
    int saveAll(List<WeatherLog> entities);

    List<WeatherLog> findLatestLogs(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

    WeatherLog findLatestLogOfAll();
}
