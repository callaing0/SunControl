package com.suncontrol.core.repository.log;

import com.suncontrol.core.entity.log.RadiationLog;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RadiationLogRepository {
    int saveAll(List<RadiationLog> entities);
    //todo
}
