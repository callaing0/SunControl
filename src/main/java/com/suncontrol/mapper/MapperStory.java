package com.suncontrol.mapper;

import com.suncontrol.domain.dto.AlertDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Insert;

@Mapper
public interface MapperStory { // 인터페이스 이름을 MapperStory로 변경
    @Insert("INSERT INTO alert_log (location, efficiency, status, message) " +
            "VALUES (#{location}, #{efficiency}, #{status}, #{message})")
    int insertSolarLog(AlertDTO data);
}