package com.suncontrol.core.repository.log;

import com.suncontrol.core.entity.log.DailyWeather;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface DailyWeatherRepository {
    int saveAll(List<DailyWeather> entities);

    List<DailyWeather> findLatestLogs(
            @Param("start") LocalDate start, @Param("end") LocalDate end);
    //todo
}
