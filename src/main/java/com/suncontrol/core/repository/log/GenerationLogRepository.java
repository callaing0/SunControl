package com.suncontrol.core.repository.log;

import com.suncontrol.core.dto.log.GenerationLogUpdateStatusDto;
import com.suncontrol.core.dto.log.LastGeneratedTime;
import com.suncontrol.core.entity.log.GenerationLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Mapper
public interface GenerationLogRepository {
    int saveAll(@Param("list") List<GenerationLog> list);

    List<LastGeneratedTime> findLastsOf();

    List<GenerationLog> findAllByStatus(String status);

    int updateStatus(List<GenerationLogUpdateStatusDto> dtoList);

    List<GenerationLog> findAllByNotStatus(String status);

    List<GenerationLog> findAllBetweenTimeByStatus(
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end,
            @Param("status") String status);

    List<GenerationLog> findAllBetweenTimeByNotStatus(
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end,
            @Param("status") String status);
    //todo
}
