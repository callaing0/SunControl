package com.suncontrol.core.repository.report;

import com.suncontrol.core.entity.report.MonthlyReport;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MonthlyReportRepository {
    int saveAll(List<MonthlyReport> dataList);

    List<MonthlyReport> findAllByMonthBetween(@Param("start") String startMonth,@Param("end") String endMonth);

    List<MonthlyReport> findAllLatestByInverter();
}
