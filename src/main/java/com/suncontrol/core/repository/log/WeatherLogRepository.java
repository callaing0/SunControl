package com.suncontrol.core.repository.log;

import com.suncontrol.core.entity.log.WeatherLog;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface WeatherLogRepository {
    int saveAll(List<WeatherLog> entities);
    //todo
}
