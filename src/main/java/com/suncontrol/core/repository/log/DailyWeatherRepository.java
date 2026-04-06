package com.suncontrol.core.repository.log;

import com.suncontrol.core.entity.log.DailyWeather;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DailyWeatherRepository {
    int saveAll(List<DailyWeather> entities);
    //todo
}
