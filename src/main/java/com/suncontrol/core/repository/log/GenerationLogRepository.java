package com.suncontrol.core.repository.log;

import com.suncontrol.core.entity.log.GenerationLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface GenerationLogRepository {
    int saveAll(@Param("list") List<GenerationLog> list);
    //todo
}
