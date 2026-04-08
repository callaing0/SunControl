package com.suncontrol.mapper;

import com.suncontrol.domain.dto.AlertDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import java.util.List;

@Mapper
public interface Repository { // 인터페이스 이름을 'Repository'로 변경

    @Select("SELECT id, " +
            "detect_time as detectTime, " +
            "resolved_time as resolvedTime, " +
            "location, " +
            "status " +
            "FROM alert_log ORDER BY id DESC")
    List<AlertDTO> findAllAlerts();
}