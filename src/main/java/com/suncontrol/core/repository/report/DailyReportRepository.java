package com.suncontrol.core.repository.report;

import com.suncontrol.core.dto.report.DailyReportDto;
import com.suncontrol.core.entity.report.DailyReport;
import com.suncontrol.domain.dto.DailyGenerationChartDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Mapper
public interface DailyReportRepository {
    int saveAll(List<DailyReport> dailyReportList);

    List<DailyReport> findAllLatestByInverter(@Param("dayOffset") Integer dayOffset);

    List<DailyReport> findAllByDateBetween(@Param("start") LocalDate start,@Param("end") LocalDate end,@Param("dayOffset") Integer dayOffset);

    List<DailyReport> findByPlantIdDateBetween(@Param("pId") Long pId, @Param("start") LocalDate start,@Param("end") LocalDate end,@Param("dayOffset") Integer dayOffset);

    BigDecimal sumValueActualByInverterIdAndBaseDateBetween(
            @Param("inverterId") Long inverterId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );
    List<DailyReportDto> findDailyReportListByMonth(@Param("plantId") Long plantId,
                                                    @Param("baseMonth") String baseMonth);
    //todo
}
