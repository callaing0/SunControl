package com.suncontrol.mapper;

import com.suncontrol.domain.dto.AlertDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Insert;

@Mapper
public interface SolarMapper {
    // 발전 기록 및 알림 상태 저장
    @Insert("INSERT INTO solar_monitoring (location, efficiency, status, message) " +
            "VALUES (#{location}, #{efficiency}, #{status}, #{message})")
    int insertSolarLog(AlertDTO data);
}