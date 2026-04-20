package com.suncontrol.core.repository.report;

import com.suncontrol.core.entity.report.HourlyReport;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface HourlyReportRepository {
    List<HourlyReport> findAllLatestByInverter(@Param("dayOffset") int dayOffset);

    int saveAll(List<HourlyReport> hourlyReportList);

    List<HourlyReport> findAllByBaseTimeBetween(
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end,
            @Param("dayOffset") int dayOffset
    );
}
