package com.suncontrol.core.repository.report;

import com.suncontrol.core.entity.report.DailyReport;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface DailyReportRepository {
    int saveAll(List<DailyReport> dailyReportList);

    List<DailyReport> findAllLatestByInverter(@Param("dayOffset") Integer dayOffset);

    List<DailyReport> findAllByDateBetween(@Param("start") LocalDate start,@Param("end") LocalDate end,@Param("dayOffset") Integer dayOffset);
    //todo
}
