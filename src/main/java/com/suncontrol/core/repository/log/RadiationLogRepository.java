package com.suncontrol.core.repository.log;

import com.suncontrol.core.entity.log.RadiationLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface RadiationLogRepository {
    int saveAll(List<RadiationLog> entities);

    List<RadiationLog> findLatestLogs(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);
    //todo
}
