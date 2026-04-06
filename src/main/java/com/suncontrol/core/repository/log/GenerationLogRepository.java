package com.suncontrol.core.repository.log;

import com.suncontrol.core.entity.log.GenerationLog;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface GenerationLogRepository {
    int saveAll(List<GenerationLog> list);
    //todo
}
