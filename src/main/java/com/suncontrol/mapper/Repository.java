package com.suncontrol.mapper; // 리포지토리 패키지도 domain 하위로 맞추는 게 일반적입니다

import com.suncontrol.domain.dto.AlertDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import java.util.List;

@Mapper
public interface Repository {

    @Select("SELECT alert_id as id, " +
            "DATE_FORMAT(detect_time, '%Y-%m-%d %H:%i:%s') as detectTime, " +
            "DATE_FORMAT(resolved_time, '%Y-%m-%d %H:%i:%s') as resolvedTime, " +
            "location, status, efficiency " +
            "FROM alert_log ORDER BY detect_time DESC")
    List<AlertDTO> findAll();

    @Select("SELECT alert_id as id, " +
            "DATE_FORMAT(detect_time, '%Y-%m-%d %H:%i:%s') as detectTime, " +
            "DATE_FORMAT(resolved_time, '%Y-%m-%d %H:%i:%s') as resolvedTime, " +
            "location, status, efficiency " +
            "FROM alert_log WHERE location = #{location} ORDER BY detect_time DESC")
    List<AlertDTO> findByLocation(@Param("location") String location);
}