package com.suncontrol.mapper;

import com.suncontrol.domain.dto.AlertDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Insert;

@Mapper
public interface SolarMapper {
    @Insert("INSERT INTO alert_log (location, efficiency, message, status) " +
            "VALUES (#{location}, #{efficiency}, #{message}, #{status})")
    int insertAlertLog(AlertDTO alert);
}