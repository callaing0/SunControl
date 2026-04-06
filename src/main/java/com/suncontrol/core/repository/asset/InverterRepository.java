package com.suncontrol.core.repository.asset;

import com.suncontrol.core.dto.asset.InverterCapSurplusDto;
import com.suncontrol.core.entity.asset.Inverter;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface InverterRepository {
    void save(Inverter inverter);

    void updateCap(InverterCapSurplusDto dto);
}
