package com.suncontrol.core.repository.report;

import com.suncontrol.core.entity.report.MonthlyReport;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

@Mapper
public interface MonthlyReportRepository {
    int saveAll(List<MonthlyReport> dataList);

    List<MonthlyReport> findAllByMonthBetween(@Param("start") String startMonth,@Param("end") String endMonth);

    List<MonthlyReport> findAllLatestByInverter();

    MonthlyReport findByInverterIdAndBaseMonth(@Param("inverter_id") Long inverterId,@Param("base_month") YearMonth baseMonth);
    //todo
}
