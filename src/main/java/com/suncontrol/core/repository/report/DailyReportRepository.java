package com.suncontrol.core.repository.report;

import com.suncontrol.core.entity.report.DailyReport;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DailyReportRepository {
    int saveAll(List<DailyReport> dailyReportList);

    List<DailyReport> findAllLatestByInverter(@Param("dayOffset") Integer dayOffset);
    //todo
}
